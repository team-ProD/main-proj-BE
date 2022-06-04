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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/**").permitAll() //그외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.

        //remeber-me : 자동로그인 기능 설정
        http.rememberMe()
                .key("uniqueAndSecret") //인증받은 사용자의 정보로 token을 생성하는데 사용되는 key값을 설정한다.(임의 값 설정)
                .rememberMeParameter("remember-me") //token을 생성하기 위한 파라미터
                .tokenValiditySeconds(86400 * 30) //한달 설정
                .userDetailsService(customUserDetailService); //인증하는데 필요한 UserDetailService를 넣어줘야 한다. 없다면 만들어야 한다. 필수다!
//                .authenticationSuccessHandler(loginSuccessHandler()) //remember-me로 로그인 성공했을때, 액션에 대해서 정의해줄 수 있는 handler

        //로그인 폼 커스텀 페이지로 구현
        http.formLogin()
                .loginPage("/members/login") //LoginController에서 로그인을 처리하는 url 경로를 써준다. (여기서 로그인 처리란, id,pw 검증 및 토큰 생성하는 메소드가 지정된 url)
                .defaultSuccessUrl("/welcome");


    }

    //로그인 성공 시, 해당 url 로 redirect 처리해주는 handler
    @Bean
    public LoginSuccessHandler loginSuccessHandler () {
        LoginSuccessHandler handler = new LoginSuccessHandler();
        handler.setDefaultTargetUrl("/");

        return handler;
    }


}
