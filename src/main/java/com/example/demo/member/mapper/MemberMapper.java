package com.example.demo.member.mapper;


import com.example.demo.member.vo.MemberVO;

import com.example.demo.member.vo.ProfileVO;
import com.example.demo.security.vo.UserVO;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {


  int save(MemberVO memberVO);

  int chgPass(MemberVO memberVO);

  ProfileVO getProfile(ProfileVO profileVO);

  List<ProfileVO> getProfileList(ProfileVO profileVO);

  int updateTempProfile(ProfileVO profileVO);

  int updateProfile(ProfileVO profileVO);

}
