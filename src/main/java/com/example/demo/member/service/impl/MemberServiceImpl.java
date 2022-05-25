package com.example.demo.member.service.impl;

import com.example.demo.member.mapper.MemberMapper;
import com.example.demo.member.service.MemberService;
import com.example.demo.member.vo.ToolVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  MemberMapper memberMapper;

  @Override
  public List<ToolVO> getToolList(ToolVO toolVO) {

    return memberMapper.getToolList(toolVO);
  }

}
