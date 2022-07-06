package com.example.demo.emailSender.mapper;

import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailSenderMapper {

    MemberVO getUserVOfromEmail(String email);

    MemberVO getUserVOfromId(int id);



    int resetPassword(int id);
}
