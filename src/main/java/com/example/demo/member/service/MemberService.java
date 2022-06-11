package com.example.demo.member.service;


import com.example.demo.member.vo.ProfileVO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

public interface MemberService {


import com.example.demo.member.vo.MemberVO;
import com.example.demo.security.vo.UserVO;

public interface MemberService {
  // 시큐리티와 상관없는 로직은 Member로 가는게 좋을 듯합니다.
  public int saveUser(MemberVO memberVO);

  public int chgPassword(MemberVO memberVO);

}
