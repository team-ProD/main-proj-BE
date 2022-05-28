package com.example.demo.member.service;

import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.vo.UserVO;

public interface UserService {

    public int saveUser(UserVO user);
}
