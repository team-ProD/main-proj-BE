package com.example.demo.security.rememberMe;

import com.example.demo.security.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

@Component
public class RememberMeCheckFilter implements Filter{

    Authentication authentication;

    //토큰을 생성하고 검증하는 컴포넌트 클래스
    private final JwtTokenProvider jwtTokenProvider;

    public RememberMeCheckFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String userId = null;

        System.out.println("###################### RememberMe Check Filter 입니다 ########################################################################");
        String rememberMeCookie = extractRememberMeCookie((HttpServletRequest) request);
        
        //쿠키에 remeberMe 가 있다면 token 값 새로 발급
        if(rememberMeCookie != null) {
            userId = extractUserId(rememberMeCookie);
            System.out.println("################## 사용자 ID: " + userId);
            insertTokenOnCookie(userId, (HttpServletResponse)response);
        }

        chain.doFilter(request, response);
    }

    public void insertTokenOnCookie(String userId, HttpServletResponse response){
        final String user = userId;
        final List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        final String token = jwtTokenProvider.createToken(user,roles);
        Cookie cookie = new Cookie("jwt",token);
        cookie.setPath("/");
        response.addHeader("Authorization", "BEARER"+ " " + token); // jwt 토큰을 헤더로 넘기고 싶으면!
        response.addCookie(cookie); // jwt 토큰을 쿠키로 넘기고 싶으면!
    }

    //RememberMe 에 있는 사용자 ID 값 가져오기
    public String extractUserId(String rememberMeCookie){
        String decodeResult = null;
        String[] rememberInfo = null;

        //remeber-me decode 하기
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(rememberMeCookie);
        decodeResult = new String(decodedBytes);
        rememberInfo = decodeResult.split(":");
        return rememberInfo[0];
    }
    
    //쿠키에 RememberMe 가져오기
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