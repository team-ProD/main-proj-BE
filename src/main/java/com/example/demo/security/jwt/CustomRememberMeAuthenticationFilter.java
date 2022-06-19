package com.example.demo.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter {


    public CustomRememberMeAuthenticationFilter(AuthenticationManager authenticationManager, RememberMeServices rememberMeServices) {
        super(authenticationManager, rememberMeServices);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication auth = autoLogin((HttpServletRequest) request, (HttpServletResponse) response);

        chain.doFilter(request, response);
    }


    public final Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        String rememberMeCookie = extractRememberMeCookie(request);
        System.out.println("remember-me 값: " + rememberMeCookie);


        return null;
    }

    public String extractRememberMeCookie(HttpServletRequest request){
        String rememberMeCookie = null;
        Cookie[] cookies=request.getCookies(); // 모든 쿠키 가져오기
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기
                if (name.equals("remember-me")) {
                    rememberMeCookie = value;
                    return rememberMeCookie;
                }
            }
        }

        return rememberMeCookie;
    }
}
