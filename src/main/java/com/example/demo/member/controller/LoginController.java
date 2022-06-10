package com.example.demo.member.controller;

import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final UserServiceImpl useService;

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/login?logout")
    public String logout(){
        return "/";
    }

}
