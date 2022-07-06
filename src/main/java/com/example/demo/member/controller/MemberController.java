package com.example.demo.member.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.service.impl.MemberServiceImpl;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Collections;

@RestController
public class MemberController {




  @Autowired
  PasswordEncoder passwordEncoder; // RequiredConstructor 써도 되는데 다른 방식도 보여주고 싶었습니당.
  @Autowired
  MemberServiceImpl memberService;



  /**
   * 회원가입
   * @param memberVO
   * @return
   */
  @PostMapping("/members/join")
  public ResponseEntity<Message> join(@RequestBody MemberVO memberVO ){ // lombok이 있으니 Map을 쓸필요가없어서 고쳤습니다
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.set("demo", "Join"); // 헤더에 왜 넣어주는지 나중에 알려주세요 (몰라서)
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;

    try{
      // lombok이 있으니 Map을 쓸필요가없어서 고쳤습니다
      int result = memberService.saveUser(MemberVO.builder()
          .email(memberVO.getEmail())
          .password(passwordEncoder.encode(memberVO.getPassword()))
          .roles(Collections.singletonList("ROLE_USER"))
          .name(memberVO.getName())
          .privacyAgree(memberVO.getPrivacyAgree())  // 1,0으로 넣으셨는데 Y,N도 괜찮을듯~!
          .remoteLoginAgree(memberVO.getRemoteLoginAgree()) //
          .build());
      // 가입완료된 회원데이터 프론트에 보내주면 쓸데가 많을 것같아서 보내주기.
      message.getData().put("result",result); // 조회시 보낼 데이터 이렇게 넣어주세요
      //회원가입이 완료된 경우 1을 반환하기에 1보다 작은 result 값은 회원가입을 성공하지 못했다는 것.
      if(result < 1){
        throw new Exception();
      }
      message.setStatus(HttpStatus.OK.value());
      message.setMessage("회원가입을 성공하였습니다.");
    }catch (Exception e){
      message.setStatus(HttpStatus.BAD_REQUEST.value());
      message.setMessage("회원가입을 실패하였습니다.");
      e.printStackTrace();
    }

    return new ResponseEntity<>(message, headers, status);
  }


  @PutMapping("/members/password/{email}")
  public ResponseEntity<Message> changePassword(@RequestBody MemberVO memberVO, @PathVariable String email) {
    int dbResult = 0;
    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();
    headers.set("demo", "ChangePassword");


    memberVO.setEmail(email);
    memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));

    try{
      dbResult = memberService.chgPassword(memberVO);
      System.out.println("변경된 값: " + dbResult);
      if(dbResult <= 0){
        throw new Exception();
      }else {
        message.setMessage("비밀번호 변경을 성공하였습니다.");
        message.setStatus(HttpStatus.OK.value());
      }
    }catch (Exception e){
      message.setMessage("비밀번호 수정을 실패하였습니다.");
      message.setStatus(HttpStatus.BAD_REQUEST.value());
      e.printStackTrace();
    }

    return ResponseEntity.status(message.getStatus()).headers(headers).body(message);
  }

  //권한 확인
  @GetMapping("/api/mypage")
  @PreAuthorize("hasAnyRole('USER')") //해당 메서드가 호출되기 이전에 권한을 검사한다. 현재 사용자의 권한이 파라미터의 권한 중 일치하는 것이 있는 경우 true 를 리턴
  public String mypage() {
    return "권한이 필요한 페이지 요청 받음";

  }


}
