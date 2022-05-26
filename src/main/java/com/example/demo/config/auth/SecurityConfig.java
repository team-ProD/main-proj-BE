package com.example.demo.config.auth;

import com.example.demo.common.oauth.entity.Role;
import com.example.demo.common.oauth.service.CustomOAuth2MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2MemberService customOAuth2MemberService;

    public SecurityConfig(CustomOAuth2MemberService customOAuth2MemberService) {
        this.customOAuth2MemberService = customOAuth2MemberService;
    }

    // 권한에 따라 자신의 웹서비스를 어디까지 공개할지 작성
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig 진입 :: ");
        http
                // csrf 차단 해제
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/",
                        "/home/**",
                        "/test/**"
                )
                // 회원가입하지 않은 유저도 확인할 수 있는 페이지
                .permitAll()
                // 권한을 가진 유저만 접속할 수 있는 페이지 설정
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2MemberService);
    }
}
