package com.example.demo.security.controller;


import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.impl.UserServiceImpl;
import com.example.demo.common.vo.MessageVO;
import com.example.demo.member.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final UserServiceImpl useService;

    // 회원가입
    @PostMapping("/members/join")
    public ResponseEntity<Message> join(@RequestBody UserVO user){
        HttpHeaders headers = new HttpHeaders();
        headers.set("demo", "Join");
        MessageVO messageVO = new MessageVO();

        try{
            int result = useService.saveUser(UserVO.builder()
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .name(user.getName())
                    .privacyAgree(user.getPrivacyAgree())
                    .remoteLoginAgree(user.getRemoteLoginAgree())
                    .build());

            //회원가입이 완료된 경우 1을 반환하기에 1보다 작은 result 값은 회원가입을 성공하지 못했다는 것.
            if(result < 1){
                throw new Exception();
            }else{
                messageVO.setMessage("회원가입을 성공하였습니다.");
                messageVO.setStatus(HttpStatus.OK.value());
            }
        }catch (Exception e){
            messageVO.setMessage("회원가입을 실패하였습니다.");
            messageVO.setStatus(HttpStatus.BAD_REQUEST.value());
            e.printStackTrace();
        }

        return ResponseEntity;
    }

    @PutMapping("/members/password/{email}")
    public ResponseEntity<MessageVO> changePassword(@RequestBody Map<String, String> user, @PathVariable String email) {
        int dbResult = 0;
        MessageVO messageVo = new MessageVO();
        HttpHeaders headers = new HttpHeaders();
        headers.set("demo", "ChangePasswrod");

        try{
            dbResult = useService.chgPassword(email, user.get("password"));
            if(dbResult <= 0){
                throw new Exception();
            }else {
                messageVo.setMessage("비밀번호 변경을 성공하였습니다.");
                messageVo.setStatus(HttpStatus.OK.value());
            }
        }catch (Exception e){
            messageVo.setMessage("비밀번호 수정을 실패하였습니다.");
            messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
            e.printStackTrace();
        }

        return ResponseEntity.status(messageVo.getStatus()).headers(headers).body(messageVo);
    }

    //권한 확인
    @GetMapping("/api/mypage")
    @PreAuthorize("hasAnyRole('USER')") //해당 메서드가 호출되기 이전에 권한을 검사한다. 현재 사용자의 권한이 파라미터의 권한 중 일치하는 것이 있는 경우 true 를 리턴
    public String mypage() {
        return "권한이 필요한 페이지 요청 받음";

    }

}
