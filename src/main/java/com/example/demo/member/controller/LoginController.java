package com.example.demo.member.controller;

import com.example.demo.member.jwt.JwtTokenProvider;
import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.impl.UserServiceImpl;
import com.example.demo.member.vo.MessageVO;
import com.example.demo.member.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final UserServiceImpl useService;

    @GetMapping("/members/login")
    public String login(){
        return "loginPage";
    }

}
