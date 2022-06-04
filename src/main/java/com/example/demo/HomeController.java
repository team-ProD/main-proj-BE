package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Hyunsik Lee on 2022-03-31. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String Home() {
        return "home";
    }

    @GetMapping("/welcome")
    public String Welcome() {
        return "welcome";
    }

}
