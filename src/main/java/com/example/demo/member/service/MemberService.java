package com.example.demo.member.service;

import com.example.demo.member.vo.ProfileVO;
import com.example.demo.member.vo.ToolVO;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

public interface MemberService {

    List<ToolVO> getToolList(ToolVO toolVO);
}
