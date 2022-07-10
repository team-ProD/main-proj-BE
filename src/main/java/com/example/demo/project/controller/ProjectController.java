package com.example.demo.project.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.vo.MemberVO;
import com.example.demo.project.service.impl.ProjectServiceImpl;
import com.example.demo.project.vo.ProjectVO;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@Log4j2
// @RequestMapping("/api")
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectService;

    /**
     * 프로젝트 생성
     *
     * @param projectVO
     * @return
     */
    @PostMapping(value = "/project")
    public ResponseEntity<Message> projectCreate(@RequestBody ProjectVO projectVO, @RequestParam(required = false) MultipartFile[] files) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            projectService.createProject(projectVO, files);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트 생성이 완료되었습니다.");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    @GetMapping(value = "/project/{id}")
    public ResponseEntity<Message> projectSelect(@PathVariable int id) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        ProjectVO projectVO = new ProjectVO();
        projectVO.setId(id);
        try {
            ProjectVO vo = projectService.selectProject(projectVO);
            if (vo != null) {
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("프로젝트 조회 완료");
                message.getData().put("project", vo);
            } else {
                message.setStatus(HttpStatus.BAD_REQUEST.value());
                message.setMessage("조회된 프로젝트가 없습니다.");
            }


        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    // TODO 이미지 변경도 같이 들어가는지 확인하고 필요시 넣을 것
    @PatchMapping(value = "/project")
    public ResponseEntity<Message> projectUpdate(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            projectService.updateProject(projectVO);

            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트 수정 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }


    /**
     * 사용자 초대
     * @param projectVO
     * @return
     */
    @PostMapping(value = "/project/attendant")
    public ResponseEntity<Message> userAttendant(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //TODO 사용자 아이디 존재 여부 조회

        try {
            projectService.userAttendant(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("초대 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 초대 수락
     * @param projectVO
     * @return
     */
    @PatchMapping(value = "/project/attendant")
    public ResponseEntity<Message> attendantAccept(@RequestBody ProjectVO projectVO)  {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //TODO 초대가 유효한지 확인
        //1.member가 유효한지 확인
        //2.profile이 유효한지 혹인
        //3.attendant가 유효한지 확인
        try {
            projectService.attendantAccept(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("초대수락 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 사용자별 프로젝트 조회
     */
    @GetMapping(value = "/projects/{memberId}")
    public ResponseEntity<Message> getProjectByMemberId(@PathVariable int memberId,@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        projectVO.setMemberId(memberId);
        try {
            List<ProjectVO> list = projectService.getProjectByMemberId(projectVO);
            if (list != null) {
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("프로젝트 조회 완료");
                message.getData().put("projectList", list);
            } else {
                message.setStatus(HttpStatus.BAD_REQUEST.value());
                message.setMessage("조회된 프로젝트가 없습니다.");
            }
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 초대받은 프로젝트 갯수 체크
     */
    @GetMapping(value = "/projects/invited/{memberId}")
    public ResponseEntity<Message> getProjectCntInvited(@PathVariable int memberId,@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        projectVO.setMemberId(memberId);
        try {
            int cnt = projectService.getProjectCntInvited(projectVO);
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("초대받은 프로젝트 조회 완료");
                message.getData().put("cnt", cnt);

        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 프로젝트 생성 > 팀구성 (추가)
     */
    @PostMapping(value = "/team")
    public ResponseEntity<Message> addNewTeam(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //TODO 사용자 아이디 존재 여부 조회

        try {
            projectService.addNewTeam(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("팀 추가 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 팀구성원 추가
     * @param projectVO
     * @return
     */
    @PatchMapping(value = "/project/team/member")
    public ResponseEntity<Message> updateProfileWithTeam(@RequestBody ProjectVO projectVO)  {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            projectService.updateProfileWithTeam(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("팀구성원 추가 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 초대된 멤버리스트 노출
     */
    @GetMapping(value = "/project/members/invited")
    public ResponseEntity<Message> getMembersInvited(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            List<MemberVO> list = projectService.getMembersInvited(projectVO);
            if (list != null) {
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("초대된 멤버 조회 완료");
                message.getData().put("memberList", list);
            } else {
                message.setStatus(HttpStatus.BAD_REQUEST.value());
                message.setMessage("조회된 멤버가 없습니다.");
            }
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 사용중인 협업툴 링크 조회
     */
    @GetMapping(value = "/project/tools")
    public ResponseEntity<Message> getToolInfoByProjectId(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        try {
            List<ProjectVO> list = projectService.getToolInfoByProjectId(projectVO);
            if (list != null) {
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("사용중인 협업툴 조회 완료");
                message.getData().put("memberList", list);
            } else {
                message.setStatus(HttpStatus.BAD_REQUEST.value());
                message.setMessage("사용중인 협업툴이 없습니다.");
            }
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 사용할 협업툴 등록
     */
    @PostMapping(value = "/project/tool")
    public ResponseEntity<Message> addNewTool(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //TODO 사용자 아이디 존재 여부 조회

        try {
            projectService.addNewTool(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("협업툴 등록 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
    /**
     * 프로젝트 내에 팀 정보 조회
     */
    @GetMapping(value = "/project/teams")
    public ResponseEntity<Message> getTeamInfo(@PathVariable int memberId,@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        projectVO.setMemberId(memberId);
        try {
            List<ProjectVO> list = projectService.getTeamInfo(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("팀 정보 조회 완료");
            message.getData().put("list", list);

        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }


    /**
     * 프로젝트 룸 생성
     */
    @PostMapping(value = "/project/room")
    public ResponseEntity<Message> addProjectRoom(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //TODO 사용자 아이디 존재 여부 조회

        try {
            projectService.createProjectRoom(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트룸 등록 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 프로젝트 룸 조회
     */
    @GetMapping(value = "/project/room")
    public ResponseEntity<Message> getProjectRoomInfo(@PathVariable int memberId,@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        projectVO.setMemberId(memberId);
        try {
            List<ProjectVO> list = projectService.getProjectRoom(projectVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트 룸 정보 조회 완료");
            message.getData().put("list", list);

        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }

    /**
     * 프로젝트 룸 정보 수정
     */
    @PatchMapping(value = "/project/room")
    public ResponseEntity<Message> projectRoomUpdate(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            projectService.updateProjectRoom(projectVO);

            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트 룸  수정 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }


    /**
     * 프로젝트 룸  삭제
     */
    @DeleteMapping(value = "/project/room")
    public ResponseEntity<Message> delProjectRoom(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            projectService.delProjectRoom(projectVO);

            message.setStatus(HttpStatus.OK.value());
            message.setMessage("프로젝트 룸  삭제 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
}