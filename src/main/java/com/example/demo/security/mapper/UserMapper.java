package com.example.demo.security.mapper;

import com.example.demo.member.vo.MemberVO;
import com.example.demo.security.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface UserMapper {

    int save(UserVO userVO);
    UserVO findByEmail(String email);

}
