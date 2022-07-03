package com.example.demo.emailSender.controller;

import com.example.demo.emailSender.service.EmailService;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    @Autowired
    PasswordEncoder passwordEncoder; // RequiredConstructor 써도 되는데 다른 방식도 보여주고 싶었습니당.

    @Autowired
    EmailService emailService;

    //비밀번호 변경 폼 보여주기
    @GetMapping(value = "/email/passwordForm/{id}")
    public String viewPasswordForm(@PathVariable int id) {

        return "/chgPassword";
    }

    //이메일 변경
    @PostMapping(value = "/email/chgPassword/")
    public String viewPasswordForm(@RequestParam("beforePassword") String beforePassword, @RequestParam("afterPassword") String afterPassword) {
        System.out.println("변경전 비밀번호: " + beforePassword + " 변경후 비밀번호: " + afterPassword);
        MemberVO memberVO = null;
        System.out.println("passwordEncoder : " + passwordEncoder.encode(beforePassword));
        memberVO = emailService.chkPassword(passwordEncoder.encode(beforePassword));
//        memberVO = emailService.chkPassword(beforePassword);
        System.out.println("memberVO : " + memberVO);
        return "/";
    }
}
