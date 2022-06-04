package com.example.demo;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Data
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");

        response.sendRedirect("/welcome");
    }

    public void setDefaultTargetUrl(String s) {
        //로그인 성공시 url 값을 세팅해준다.
        setUrl(s);
    }
}
