package com.example.demo.member.mapper;

import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    MemberVO findByEmail(String email);   // 이메일 기반 사용자 조회
    MemberVO login(MemberVO memberVO);   // 사용자 로그인
    int signUp(MemberVO memberVO);       // 회원가입

}
