package com.example.demo;


import com.example.demo.member.jwt.JwtAuthenticationFilter;
import com.example.demo.member.jwt.JwtTokenProvider;
import com.example.demo.member.service.impl.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //토큰을 생성하고 검증하는 컴포넌트 클래스
    private final JwtTokenProvider jwtTokenProvider;

    //remember-me 기능에 필요한 서비스 클래스
    @Autowired
    private CustomUserDetailService customUserDetailService;

    //암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    // authenticationManager 를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable() //rest api 만을 고려하여 기본 설정은 해제합니다.
                .csrf().disable() //csrf 보안 토큰 disable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //토큰 기반 인증이되므로 세션 역시 사용되지 않습니다.
                .and()
                .authorizeHttpRequests() //요청에 대한 사용권한 체크
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/**").permitAll() //그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.

        //로그인 폼 커스텀 페이지로 구현
        http.formLogin()
                .defaultSuccessUrl("/welcome") //로그인 성공 시 url
                .loginPage("/login") //커스텀 로그인 폼의 url 경로를 작성(권한이 필요한 페이지에 로그인이 안된 경우, 자동으로 여기에 적은 url 폼으로 이동)
                .loginProcessingUrl("/members/login") //loginForm에서 로그인을 처리하는 action url 경로를 써준다. (여기서 로그인 처리란, Controller 에서 id,pw 검증 및 토큰 생성하는 메소드가 지정된 url)
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                System.out.println("authentication : " + authentication.getName());
                                response.sendRedirect("/welcome"); // 인증이 성공한 후에는 root로 이동
                            }
                        }
                )
                .failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                System.out.println("exception : " + exception.getMessage());
                                response.sendRedirect("/login");
                            }
                        }
                );

        //remeber-me : 자동로그인 기능 설정
        http.rememberMe()
                .key("uniqueAndSecret") //인증받은 사용자의 정보로 token을 생성하는데 사용되는 key값을 설정한다.(임의 값 설정)
                .rememberMeParameter("remember-me") //token을 생성하기 위한 파라미터
                .tokenValiditySeconds(86400 * 30) //한달 설정
                .userDetailsService(customUserDetailService);
        //인증하는데 필요한 UserDetailService를 넣어줘야 한다. 없다면 만들어야 한다. 필수다!

        http.logout()
                .deleteCookies("JSESSIONID");



    }



}
