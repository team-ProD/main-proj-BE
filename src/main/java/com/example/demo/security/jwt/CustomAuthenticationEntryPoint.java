package com.example.demo.security.jwt;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LHS
 * @version 1.0.0
 * @since 2022-10-29 오후 3:36
 */
@Component
@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");

        log.debug("log: exception: {} ", exception);

        /**
         * 토큰 없는 경우
         */
        if(exception == null) {

            setResponse(response, 000);
            return;
        }

        /**
         * 토큰 만료된 경우
         */
        if(exception.equals("expiredToken")) {
            setResponse(response, 111);
            return;
        }

        /**
         * 토큰 시그니처가 다른 경우
         */
        if(exception.equals("3") ){
            setResponse(response, 3);
        }
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response, int errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (errorCode == 000) {
            response.getWriter().write(
                    "{ \"message\" : \"" + "로그인 정보가 없습니다."
                            + "\", \"status\" : " + errorCode + "}");
            response.setStatus(200);
        } else if (errorCode == 111) {
            response.getWriter().write(
                    "{ \"message\" : \"" + "로그인 정보가 만료되었습니다."
                            + "\", \"status\" : " + errorCode + "}");
            response.setStatus(200);
        }

    }

}
