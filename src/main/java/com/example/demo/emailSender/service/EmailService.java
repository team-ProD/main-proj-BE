package com.example.demo.emailSender.service;

import com.example.demo.emailSender.mapper.EmailMapper;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    EmailMapper emailMapper;

    public MemberVO chkPassword(String beforePassword) {
        return emailMapper.chkPassword(beforePassword);
    }
}
