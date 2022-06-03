package com.example.demo.member.service;

import com.example.demo.member.vo.MemberVO;

public interface MemberService {

    MemberVO login(MemberVO memberVO);
    void signUp(MemberVO memberVO);

}
