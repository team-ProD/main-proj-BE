package com.example.demo.member.controller;

import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {



    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/login?logout")
    public String logout(){
        return "/";
    }

}
