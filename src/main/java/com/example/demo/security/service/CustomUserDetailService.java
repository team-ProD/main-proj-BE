package com.example.demo.security.service;

import com.example.demo.security.mapper.UserMapper;
import com.example.demo.security.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    //JwtTokenProvider 클래스 파일에서 username에 따른 UserDetails 즉, User 객체를 DB에서 반환받기 원함
    //(쉽게 말하자면 UserDetails는 인터페이스, User는 그걸 구현해둔 객체!)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userDbInfo = userMapper.findByEmail(username);
        //String 타입의 role을 List로 만들어서 UserDetails에 담아야한다.
        userDbInfo.setRoles(Collections.singletonList(userDbInfo.getRole()));
        System.out.println("UserDetailsService 에서 가져온 UserDetail: "  + userDbInfo);
        return userDbInfo;
    }
}
