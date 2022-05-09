package com.example.demo.controller;

import com.example.demo.JwtTokenProvider;
import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.User;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hyunsik Lee on 2022-05-08. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@RequiredArgsConstructor
@RestController
public class UserController {
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserMapper userMapper;

  // 회원가입
  @PostMapping("/join")
  public Long join(@RequestBody Map<String, String> user) {
    return userMapper.join(User.builder()
        .email(user.get("email"))
        .password(passwordEncoder.encode(user.get("password")))
        .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
        .build()).getId();
  }

  // 로그인
  @PostMapping("/login")
  public String login(@RequestBody Map<String, String> user) {
    try {
      User member = userMapper.findByEmail(user.get("email"));

      if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
        throw new IllegalArgumentException("잘못된 비밀번호입니다.");
      }

      return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());

    } catch (Exception e) {
      throw new IllegalArgumentException("가입되지 않은 E-MAIL 입니다.");
    }
  }
}
