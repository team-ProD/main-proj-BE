package com.example.demo.member.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.vo.ProfileVO;
import com.example.demo.project.vo.ProjectVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author LHS
 * @version 1.0.0
 * @since 2022-06-28 오후 4:39
 */
@RestController
@RequestMapping("/api")
@Log4j2
public class ProfileController {

    @Autowired
    MemberService memberService;

    /**
     * 프로필 조회 (작성여부도 같이 겸사겸사)
     */
    @GetMapping(value = "/profile/{memberId}/{projectId}")
    public ResponseEntity<Message> getProfile(@PathVariable String memberId, @PathVariable String projectId, ProfileVO profileVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        profileVO.setMemberId(memberId);
        profileVO.setProjectId(projectId);
        try {
            ProfileVO vo = memberService.getProfile(profileVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("멤버 프로필 조회 완료");
            message.getData().put("profile", vo);

        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
    /**
     * 임시저장
     */
    @PatchMapping(value = "/profile/temp")
    public ResponseEntity<Message> updateTempProfile(ProfileVO profileVO)  {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            memberService.updateTempProfile(profileVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("임시저장 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
    /**
     * 프로필 수정완료
     */
    @PatchMapping(value = "/profile")
    public ResponseEntity<Message> updateProfile(ProfileVO profileVO)  {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        try {
            memberService.updateProfile(profileVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("저장 완료");
        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
    /**
     * 프로젝트 참여자 프로필 조회 , 툴에 대한 아이디, 전화번호 등의 정보도 같이!
     */
    @GetMapping(value = "/profiles/{projectId}")
    public ResponseEntity<Message> getProfile( @PathVariable String projectId, ProfileVO profileVO) {
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        profileVO.setProjectId(projectId);
        try {
            List<ProfileVO> list = memberService.getProfileList(profileVO);
            message.setStatus(HttpStatus.OK.value());
            message.setMessage("멤버 프로필 조회 완료");
            message.getData().put("list", list);

        } catch (Exception e) {
            message.setMessage(String.valueOf(e));
            log.info(e.getMessage());
        }

        return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
    }
}
