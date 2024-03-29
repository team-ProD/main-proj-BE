package com.example.demo.emailSender.controller;

import com.example.demo.emailSender.service.EmailSenderService;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    //회원 가입시 인증 메일 발송
    @GetMapping(value = "/email/joinAuth/{email}")
    public void sendAuthMail(@PathVariable String email) throws MessagingException {
        MemberVO memberVO = getUserVO(email);
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
                        "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" " +
                        "   href=\"http://localhost:8080/email/join/certified/" + memberVO.getId() + "\">" +
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

    //email로 DB 조회하여 MemberVO 가져오기
    public MemberVO getUserVO(String email){
        return emailSenderService.getUserVO(email);
    }

    //id로 DB 조회하여 MemberVO 가져오기
    public MemberVO getUserVO(int id){
        return emailSenderService.getUserVO(id);

    }

    //비밀번호 변경 시 메일 발송
    @GetMapping(value = "/email/password/{id}")
    public void sendChgPasswordMail(@PathVariable int id) throws MessagingException{
        MemberVO memberVO = getUserVO(id);
        int result = -1;

        result = emailSenderService.resetPassword(id);


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
                        "		<span style=\"color: #7879f1\">비밀번호 변경</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        memberVO.getName() +
                        "		님 안녕하세요.<br />" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "		회원님의 비밀번호가 초기화 되었습니다.<br />" +
                        "		아래 <b style=\"color: #7879f1\">'비밀번호 변경'</b> 버튼을 클릭하여 비밀번호 변경을 진행해 주세요.<br />" +
                        "		감사합니다." +
                        "	</p>" +
                        "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" " +
                        "   href=\"http://localhost:8080/email/passwordForm/ \">" +
                        "	    <p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #7879f1; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                        "	    비밀번호 변경</p>" +
                        "</a>" +
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");
        emailSenderService.sendEmail(memberVO.getEmail(), "[ProD 비밀번호 변경]", emailcontent.toString());


    }

    //프로젝트 초대 메일 발송
    @GetMapping(value = "/email/project/invite/{email}")
    public void sendProjectInviteMail(@PathVariable String email) throws MessagingException {
        MemberVO memberVO = getUserVO(email);
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
                        "		<span style=\"color: #7879f1\">프로젝트 초대</span> 안내입니다." +
                        "	</h1>\n" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        memberVO.getName() +
                        "		님 안녕하세요.<br />" +
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                        "		프로젝트에 초대 되셨습니다. <br />" +
                        "		아래 <b style=\"color: #7879f1\">'프로젝트 초대 수락'</b> 버튼을 클릭하여 프로젝트에 참여해 주세요.<br />" +
                        "		감사합니다." +
                        "	</p>" +
                        "<a style=\"color: #FFF; text-decoration: none; text-align: center;\" " +
                        "   href=\"http://localhost:8080/email/project/participate/" + memberVO.getId() + "\">" +
                        "	    <p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #7879f1; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                        "	    프로젝트 초대 수락</p>" +
                        "</a>" +
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");
        emailSenderService.sendEmail(memberVO.getEmail(), "[ProD 프로젝트 초대]", emailcontent.toString());

    }

}
