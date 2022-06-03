package com.example.demo.member.service.impl;

import com.example.demo.member.mapper.MemberMapper;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public MemberVO login(MemberVO memberVO) {
        return memberMapper.login(memberVO);
    }

    @Override
    public void signUp(MemberVO memberVO) {
        System.out.println("========================회원가입 서비스 진입 ::");
        int checkResult = memberMapper.signUp(memberVO);
        System.out.println("회원가입 결과 :: " + checkResult);
    }
}
