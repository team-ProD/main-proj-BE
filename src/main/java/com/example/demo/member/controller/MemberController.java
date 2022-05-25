package com.example.demo.member.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.service.impl.MemberServiceImpl;
import com.example.demo.member.vo.ToolVO;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  @Autowired
  MemberServiceImpl memberService;

  @GetMapping("/tools")
  public ResponseEntity<Message> getToolList(ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = memberService.getToolList(toolVO);
      message.setStatus(200);
      message.setMessage("조회 성공");
      message.getData().put("toolList",list); // 조회시 보낼 데이터 이렇게 넣어주세요
      // message.setData(); 데이터 넣을게 없음..
    } catch (Exception e) {
      System.out.println(e.getMessage());
      message.setStatus(500);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message.setMessage("조회에 실패하였습니다.");
    }
    return new ResponseEntity<>(message, headers, status);
  }

}
