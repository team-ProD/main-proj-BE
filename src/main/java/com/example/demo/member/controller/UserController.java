package com.example.demo.member.controller;


import com.example.demo.common.vo.Message;
import com.example.demo.SpringSecurity.jwt.JwtTokenProvider;
import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.impl.UserServiceImpl;
import com.example.demo.member.vo.MessageVO;
import com.example.demo.member.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping("/members/join")
    public ResponseEntity<Message> join(@RequestBody Map<String, String> user){
        HttpHeaders headers = new HttpHeaders();
        headers.set("demo", "Join");
        Message messageVo = new Message();

        try{
            int result = useService.saveUser(UserVO.builder()
                    .email(user.get("email"))
                    .password(passwordEncoder.encode(user.get("password")))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .name(user.get("name"))
                    .privacyAgree(Integer.parseInt(user.get("privacy_agree")))
                    .remoteLoginAgree(Integer.parseInt(user.get("remote_login_agree")))
                    .build());

            //회원가입이 완료된 경우 1을 반환하기에 1보다 작은 result 값은 회원가입을 성공하지 못했다는 것.
            if(result < 1){
                throw new Exception();
            }else{
                messageVo.setMessage("회원가입을 성공하였습니다.");
                messageVo.setStatus(HttpStatus.OK.value());
            }
        }catch (Exception e){
            messageVo.setMessage("회원가입을 실패하였습니다.");
            messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
            e.printStackTrace();
        }

        return ResponseEntity.status(messageVo.getStatus()).headers(headers).body(messageVo);
    }
/*
    //로그인
    @PostMapping("/members/login")
//    @RequestMapping(value="/members/login", method=RequestMethod.POST, headers={"Content-type=application/json"})
    public ResponseEntity<MessageVO> login(Map<String, String> user) {
        System.out.println("/members/login 메소드에 들어온 user 정보: " + user);

        HttpHeaders headers = new HttpHeaders();
        headers.set("demo", "Login");
        MessageVO messageVo = new MessageVO();

        String roles = "";
        UserVO member = null;
        List<String> userRoles = null;
        HashMap<String,Object> data = new HashMap<>();

        try {
            member = userMapper.findByEmail(user.get("email"));
            System.out.println("회원 정보: " + member);
            if (member == null) {
                throw new IllegalArgumentException();
            }
            else if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {

                throw new IllegalArgumentException();
            }
            else {
                userRoles = member.getRoles();
                messageVo.setMessage("로그인을 성공하였습니다.");
                messageVo.setStatus(HttpStatus.OK.value());

        //      jwtTokenProvider.createToken(member의 이름(여기서는 email), rolesList);
        //      생성된 jwt 토큰값 넣기.
                data.put("jwt", jwtTokenProvider.createToken(member.getEmail(), userRoles));
                messageVo.setData(data);

            }
        }catch (IllegalArgumentException e){
            messageVo.setMessage("로그인 실패하였습니다.");
            messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
            e.printStackTrace();
        }
        catch(Exception e){
            messageVo.setMessage("로그인을 실패하였습니다.");
            messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
            e.printStackTrace();
        }

        return ResponseEntity.status(messageVo.getStatus()).headers(headers).body(messageVo);
    }
*/


//        @RequestMapping(value="/members/login", method=RequestMethod.POST, headers={"Content-type=application/json"})
        @PostMapping("/members/login")
        public String login(HttpServletRequest request) {
            System.out.println("##################################################################");
            System.out.println("여기는 /members/login 입니다.");
            System.out.println("넘어온 파라미터 값: " + request.getParameter("email"));
            System.out.println("##################################################################");

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            HttpHeaders headers = new HttpHeaders();
            headers.set("demo", "Login");
            MessageVO messageVo = new MessageVO();

            String roles = "";
            UserVO member = null;
            List<String> userRoles = null;
            HashMap<String, Object> data = new HashMap<>();

            try {
                member = userMapper.findByEmail(email);
                System.out.println("회원 정보: " + member);
                if (member == null) {
                    throw new IllegalArgumentException();
                } else if (!passwordEncoder.matches(password, member.getPassword())) {

                    throw new IllegalArgumentException();
                } else {
                    userRoles = member.getRoles();
                    messageVo.setMessage("로그인을 성공하였습니다.");
                    messageVo.setStatus(HttpStatus.OK.value());

                    //      jwtTokenProvider.createToken(member의 이름(여기서는 email), rolesList);
                    //      생성된 jwt 토큰값 넣기.
                    data.put("jwt", jwtTokenProvider.createToken(member.getEmail(), userRoles));
                    messageVo.setData(data);

                }
            } catch (IllegalArgumentException e) {
                messageVo.setMessage("로그인 실패하였습니다.");
                messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
                e.printStackTrace();
            } catch (Exception e) {
                messageVo.setMessage("로그인을 실패하였습니다.");
                messageVo.setStatus(HttpStatus.BAD_REQUEST.value());
                e.printStackTrace();
            }

            return messageVo.toString();

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
