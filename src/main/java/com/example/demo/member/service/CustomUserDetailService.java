package com.example.demo.member.service;

import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    //JwtTokenProvider 클래스 파일에서 username에 따른 UserDetails 즉, User 객체를 DB에서 반환받기 원함
    //(쉽게 말하자면 UserDetails는 인터페이스, User는 그걸 구현해둔 객체!)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        HashMap<String, String> userDbInfo = userMapper.findByEmail(username);
        user.setEmail(userDbInfo.get("email"));
        user.setPassword(userDbInfo.get("password"));
        user.setRoles(Collections.singletonList(userDbInfo.get("roles")));

//      return userMapper.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return user;
    }
}
