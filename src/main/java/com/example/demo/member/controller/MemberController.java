package com.example.demo.member.controller;

import com.example.demo.common.Message;
import com.example.demo.member.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/member")
public class MemberController {

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public ResponseEntity<Message> signUp(@RequestBody MemberVO memberVO) {

        System.out.println("================================회원가입 컨트롤러 진입::");

        Message message = new Message();

        try {
            System.out.println(memberVO.toString());



            message.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            message.setMessage("회원가입에 실패하였습니다.");
            e.printStackTrace();
        }

        return ResponseEntity.status(message.getStatus()).body(message);

    }

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public ResponseEntity<Message> login(@RequestBody MemberVO memberVO) {

        System.out.println("================================로그인 접근 :: ");

        Message message = new Message();

        try {
            System.out.println(memberVO.toString());
            message.setStatus(HttpStatus.OK.value());

        } catch (Exception e) {
            message.setMessage("로그인에 실패하였습니다.");
            e.printStackTrace();
        }

        return ResponseEntity.status(message.getStatus()).body(message);

    }

}
