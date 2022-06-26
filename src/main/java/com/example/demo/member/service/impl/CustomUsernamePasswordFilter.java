package com.example.demo.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // TODO something...
        System.out.println("CustomUsernamePasswordFilter 에 들어왔습니다.");

        return super.attemptAuthentication(request, response);
    }

    @Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        CustomUsernamePasswordFilter filter = new CustomUsernamePasswordFilter();
        filter.setAuthenticationManager(authenticationManager);
//        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SecurityConstant.LOGIN_URL, "POST"));
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/members/login", "POST"));
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
//        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(SecurityConstant.LOGIN_SUCCESS_URL));
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(SecurityConstant.LOGIN_URL));

        return filter;
    }
}