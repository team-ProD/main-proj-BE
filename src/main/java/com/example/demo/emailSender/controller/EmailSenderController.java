package com.example.demo.emailSender.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.emailSender.service.EmailSenderService;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.nio.charset.Charset;

@RestController
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping(value = "/email/send/{email}")
    public void sendMail(@PathVariable String email) throws MessagingException {
        MemberVO memberVO = getUserName(email);
        StringBuffer emailcontent = new StringBuffer();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" +
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #7879f1; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">ProD</span><br />" +
                        "		<span style=\"color: #7879f1\">메일인증</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                             memberVO.getName() +
                        "		님 안녕하세요.<br />" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "		ProD에 가입해 주셔서 진심으로 감사드립니다.<br />" +
                        "		아래 <b style=\"color: #7879f1\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />" +
                        "		감사합니다." +
                        "	</p>" +
//                        "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\" " +
//                        "	    href=\"http://localhost:8080/email/certified/" + memberVO.getId() + ">" +
//                        "	    <p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
//                        "	    메일 인증</p>" +
//                        "	</a>" +

                        "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" " +
                        "   href=\"http://localhost:8080/email/certified/" + memberVO.getId() + "\">" +
                        "	    <p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #7879f1; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                        "	    메일 인증</p>" +
                        "</a>" +
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");
        emailSenderService.sendEmail(memberVO.getEmail(), "[ProD 이메일 인증]", emailcontent.toString());

    }
    
    //인증 이메일의 인증 버튼 눌렀을때 certified 를 1로 바꿔주는 메소드
    @GetMapping(value = "/email/certified/{id}")
    public ResponseEntity<Message> joinCertified(@PathVariable int id){
        int result = -1;
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.set("demo", "Join Certified");
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        HttpStatus status = null;
        try {
            result = emailSenderService.joinCertified(id);
            if (result == 1) {
                status = HttpStatus.OK;
                message.setStatus(HttpStatus.OK.value());
                message.setMessage("회원가입 인증을 성공하였습니다.");
            } else {
                throw new Exception();
            }
        }catch (Exception e){
            status = HttpStatus.BAD_REQUEST;
            message.setStatus(HttpStatus.BAD_REQUEST.value());
            message.setMessage("회원가입 인증을 실패하였습니다.");
            e.printStackTrace();
        }

        return new ResponseEntity<>(message, headers, status);
    }



    //email로 DB 조회하여 MemberVO 가져오기
    public MemberVO getUserName(String email){
        return emailSenderService.getCertifiedUserName(email);
    }
}
