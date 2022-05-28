package com.example.demo.member.controller;


import com.example.demo.member.jwt.JwtTokenProvider;
import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.UserService;
import com.example.demo.member.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    private final UserService useService;

    // 회원가입
    @PostMapping("/join")
    public int join(@RequestBody Map<String, String> user) {

//        return userMapper.save(User.builder()
//                .email(user.get("email"))
//                .password(user.get("password"))
////                .roles(Collections.singletonList("ROLE_ADMIN")) // 최초 가입시 USER 로 설정
////                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
//                .roles(Collections.singletonList("ROLE_ADMIN"))
//                .build());
        return useService.saveUser(User.builder()
                .email(user.get("email"))
                .password(user.get("password"))
                .roles(Collections.singletonList("ROLE_ADMIN"))
                .build());
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        /*
        User member = userMapper.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-mail 입니다."));

        if (!passwordEncoder.matches(user.get("password"), "{noop}" + member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

         */
        String roles = "";
        HashMap<String, String> member = userMapper.findByEmail(user.get("email"));

        if(member == null){
            throw new IllegalArgumentException("가입되지 않은 E-mail 입니다.");
        }
        if (!passwordEncoder.matches(user.get("password"), "{noop}" + member.get("password"))) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> userRoles = Collections.singletonList(member.get("roles"));
//      return jwtTokenProvider.createToken(member의 이름(여기서는 email), rolesList);
        return jwtTokenProvider.createToken(member.get("email"), userRoles);
    }

    //권한 확인
    @PostMapping("/admin/myPage")
    @PreAuthorize("hasAnyRole('ADMIN')") //해당 메서드가 호출되기 이전에 권한을 검사한다. 현재 사용자의 권한이 파라미터의 권한 중 일치하는 것이 있는 경우 true 를 리턴
    public String myPage() {
        return "권한이 필요한 페이지 요청 받음";

    }

}
