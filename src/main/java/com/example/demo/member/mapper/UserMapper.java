package com.example.demo.member.mapper;

import com.example.demo.member.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public interface UserMapper {

    int save(UserVO user);

    HashMap findByEmail(String email);
}
