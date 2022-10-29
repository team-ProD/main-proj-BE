package com.example.demo.tool.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.service.impl.MemberServiceImpl;
import com.example.demo.tool.service.impl.ToolServiceImpl;
import com.example.demo.tool.vo.ToolVO;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@RestController
// @RequestMapping("/api")
public class ToolController {

  @Autowired
  ToolServiceImpl toolService;

  @GetMapping("/tools")
  public ResponseEntity<Message> getToolList(ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getToolList(toolVO);
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


  @GetMapping("/tools/master")
  public ResponseEntity<Message> getToolListWithMaster(ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getToolListWithMaster(toolVO);
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


  @GetMapping("/tools/all")
  public ResponseEntity<Message> getToolListAll( ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getToolListWithAll(toolVO);
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

  @GetMapping("/tools/used")
  public ResponseEntity<Message> getToolListWithUsed( ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getToolListWithUsed(toolVO);
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

  @GetMapping("/tools/free")
  public ResponseEntity<Message> getToolListWithFree( ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getToolListWithFree(toolVO);
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

  @GetMapping("/tools/use")
  public ResponseEntity<Message> getUsingTool(ToolVO toolVO) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      List<ToolVO> list = toolService.getUsingTool(toolVO);
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

  @PutMapping("/tool/{id}")
  public ResponseEntity<Message> updateProjectTool(@RequestBody ToolVO toolVO,@PathVariable String id) {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;
    try {
      toolVO.setId(id);
      int result = toolService.updateProjectTool(toolVO);
      message.setStatus(200);
      message.setMessage("수정 성공");
      // message.setData(); 데이터 넣을게 없음..
    } catch (Exception e) {
      System.out.println(e.getMessage());
      message.setStatus(500);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message.setMessage("수정에 실패하였습니다.");
    }
    return new ResponseEntity<>(message, headers, status);
  }

}
