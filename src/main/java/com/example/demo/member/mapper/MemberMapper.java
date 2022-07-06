package com.example.demo.member.mapper;


import com.example.demo.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {


  int save(MemberVO memberVO);

  int chgPass(MemberVO memberVO);


}
