package com.example.demo.member.mapper;

import com.example.demo.member.vo.ToolVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  List<ToolVO> getToolList(ToolVO toolVO);
}
