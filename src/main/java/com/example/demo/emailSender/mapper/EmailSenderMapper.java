package com.example.demo.emailSender.mapper;

import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailSenderMapper {

    MemberVO getUserName(String email);

    int updateCertified(int id);
}
