package com.example.demo.member.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.member.service.impl.MemberServiceImpl;
import com.example.demo.member.vo.MemberVO;
import com.example.demo.member.vo.ProfileVO;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.security.service.CustomUserDetailService;
import com.example.demo.security.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Collections;

@RestController
@RequestMapping("/api")
@Log4j2
public class MemberController {

  @Autowired
  PasswordEncoder passwordEncoder; // RequiredConstructor 써도 되는데 다른 방식도 보여주고 싶었습니당.
  @Autowired
  MemberServiceImpl memberService;

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Autowired
  CustomUserDetailService customUserDetailService;
  /**
   * JWT 로그인
   * @param uservo
   * @return
   */
  @PostMapping("/members/login")
  public ResponseEntity<Message> login(@RequestBody UserVO uservo){
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;

    try {
      UserDetails userInfo = customUserDetailService.loadUserByUsername(uservo.getUsername());
      if (userInfo == null) {
        message.setMessage("존재하지 않는 사용자입니다.");
        throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
      }
      Authentication auth = new UsernamePasswordAuthenticationToken(uservo, passwordEncoder.encode(uservo.getPassword()));
      log.info("userDto : "+uservo.getPassword());
      log.info("userInfo : "+userInfo.getPassword());
      if (!passwordEncoder.matches(uservo.getPassword(), userInfo.getPassword())) {
        message.setMessage("비밀번호가 틀립니다.");
        // matches : 평문, 암호문 패스워드 비교 후 boolean 결과 return
        throw new BadCredentialsException("비밀번호가 틀립니다.");
      }


      uservo.getRoles().add("USER");
      SecurityContextHolder.getContext().setAuthentication(auth);
      String token = jwtTokenProvider.createToken(auth.getName(),uservo.getRoles());
      message.setStatus(200);
      message.setMessage("로그인 성공!");
      message.getData().put("data",userInfo); // 조회시 보낼 데이터 이렇게 넣어주세요
      message.getData().put("token",token); // 조회시 보낼 데이터 이렇게 넣어주세요
      // message.setData(); 데이터 넣을게 없음..
    } catch(UsernameNotFoundException e){
      log.info(e.getMessage());
      message.setStatus(201);
      message.setMessage("존재하지 않는 사용자입니다.");
    }catch(BadCredentialsException e){
      log.info(e.getMessage());
      message.setStatus(202);
      message.setMessage("비밀번호가 틀립니다.");
    } catch(NullPointerException e){
      log.info(e.getMessage());
      message.setStatus(201);
      message.setMessage("존재하지 않는 사용자입니다.");
    }catch(Exception e) {
      log.info(e.getMessage());
      message.setStatus(500);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message.setMessage("로그인 실패하였습니다.");
    }

    return new ResponseEntity<>(message, headers, status);
  }

  /**
   * 회원가입
   * @param memberVO
   * @return
   */
  @PostMapping("/members/join")
  public ResponseEntity<Message> join(@RequestBody MemberVO memberVO){
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;

    try{
      memberVO = MemberVO.builder()
              .email(memberVO.getEmail())
              .password(passwordEncoder.encode(memberVO.getPassword()))
              .roles(Collections.singletonList("ROLE_USER"))
              .name(memberVO.getName())
              .privacyAgree(memberVO.getPrivacyAgree())  // 1,0으로 넣으셨는데 Y,N도 괜찮을듯~!
              .remoteLoginAgree(memberVO.getRemoteLoginAgree()) //
              .build();


      // lombok이 있으니 Map을 쓸필요가없어서 고쳤습니다
      int result = memberService.saveUser(memberVO); // 이렇게 해야 selectKey가 받아짐.


      // 가입완료된 회원데이터 프론트에 보내주면 쓸데가 많을 것같아서 보내주기.
      message.getData().put("result",memberVO); // 조회시 보낼 데이터 이렇게 넣어주세요
      ProfileVO vo = new ProfileVO();

      vo.setMemberId(memberVO.getId());
      memberService.insertProfile(vo);
      //회원가입이 완료된 경우 1을 반환하기에 1보다 작은 result 값은 회원가입을 성공하지 못했다는 것.
      if(result < 1){
        throw new Exception();
      }
      message.setStatus(HttpStatus.OK.value());
      message.setMessage("회원가입을 성공하였습니다.");
    }catch (DuplicateKeyException e){
      message.setStatus(HttpStatus.BAD_REQUEST.value());
      message.setMessage("이미 가입된 이메일입니다.");
      e.printStackTrace();
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
