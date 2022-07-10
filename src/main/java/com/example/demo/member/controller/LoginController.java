package com.example.demo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

//    @GetMapping("/login")
//    public String login(){
//        return "loginPage";
//    }

    @GetMapping("/login")
    public String login(String error, Model model){
        System.out.println("error: " + error);
        model.addAttribute("errorMessage", error);

        return "loginPage";
    }

    @GetMapping("/login?logout")
    public String logout(){
        return "/";
    }

}
