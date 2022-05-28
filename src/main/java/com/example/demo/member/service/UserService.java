package com.example.demo.member.service;

import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserMapper userMapper;

    public int saveUser(User user){
        Map<String, String> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        List<String> userRoles = user.getRoles();
        userRoles.forEach(r -> userData.put("roles", r));

        return userMapper.save(userData);
    }


}
