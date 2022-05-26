package com.example.demo.member.controller;

import com.example.demo.common.Message;
import com.example.demo.config.auth.SessionMember;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("user"));

        SessionMember member = (SessionMember)session.getAttribute("user");

        System.out.println(member);

        return "home";
    }

}
