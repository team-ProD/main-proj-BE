package com.example.demo.emailSender.mapper;

import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {
    MemberVO chkPassword(String beforePassword);

}
