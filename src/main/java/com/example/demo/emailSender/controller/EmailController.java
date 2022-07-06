package com.example.demo.emailSender.controller;

import com.example.demo.emailSender.service.EmailSenderService;
import com.example.demo.emailSender.service.EmailService;
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
    EmailSenderService emailSenderService;

    @Autowired
    PasswordEncoder passwordEncoder; // RequiredConstructor 써도 되는데 다른 방식도 보여주고 싶었습니당.

    @Autowired
    EmailService emailService;

    //비밀번호 변경 폼 보여주기
    @GetMapping(value = "/email/passwordForm")
    public String viewPasswordForm() {

        return "/chgPassword";
    }

    //이메일 변경
    @PostMapping(value = "/email/chgPassword/")
    public String viewPasswordForm(@RequestParam("afterPassword") String afterPassword) {
//        System.out.println("변경후 비밀번호: " + afterPassword);
        int dbResult = 0;

        dbResult = emailService.chgPassword(afterPassword);
        if (dbResult <= 0) {
            return "/error";
        } else {
            return "/sucess";
        }
    }

    //인증메일의 인증 버튼 눌렀을때 certified 를 1로 바꿔주는 메소드
    @GetMapping(value = "/email/join/certified/{id}")
    public String joinCertified(@PathVariable int id) {
        int result = -1;

        result = emailService.joinCertified(id);
        if (result <= 0) {
            return "/error";
        } else {
            return "/sucess";
        }

    }

    //프로젝트 초대 수락 버튼 눌렀을때 certified 를 1로 바꿔주는 메소드
    @GetMapping(value = "email/project/participate/{id}")
    public String projectParticipated(@PathVariable int id) {
        int result = -1;

        result = emailService.projectParticipated(id);
        if (result <= 0) {
            return "/error";
        } else {
            return "/sucess";
        }

    }

}
