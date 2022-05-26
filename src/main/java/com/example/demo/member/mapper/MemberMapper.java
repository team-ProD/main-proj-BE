package com.example.demo.member.mapper;

import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    // 로그인 & 회원가입
    MemberVO findByEmail(String email);
    void save(MemberVO memberVO);
}
