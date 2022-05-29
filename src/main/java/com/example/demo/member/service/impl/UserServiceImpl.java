package com.example.demo.member.service.impl;

import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.UserService;
import com.example.demo.member.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public int saveUser(UserVO user){
        //roles 가 List 형식임으로 String role 로 넣어주기
        String r = "";
        List<String> userRoles = user.getRoles();
        for(String roleData : userRoles){
            r = roleData;
        }
        user.setRole(r);
        System.out.println("유저 정보: " + user);
        return userMapper.save(user);
    }


}
