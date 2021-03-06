package com.example.demo.project.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.project.service.ProjectService;
import com.example.demo.project.service.impl.ProjectServiceImpl;
import com.example.demo.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
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
    public ResponseEntity<Message> projectCreate(ProjectVO projectVO, @RequestPart("files") MultipartFile[] files) {
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

    @GetMapping(value = "/project")
    public ResponseEntity<Message> projectSelect(@RequestBody ProjectVO projectVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            ProjectVO projectVO1 = projectService.selectProject(projectVO);
            if (projectVO1 != null) {
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("프로젝트 조회 완료");
                message.getData().put("project", projectVO1);
            } else {
                message.setStatus(HttpStatus.BAD_REQUEST.value());
                message.setMessage("조회된 프로젝트가 없습니다.");
            }


        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }


    @PutMapping(value = "/project")
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
    @PutMapping(value = "/project/attendant")
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
}
