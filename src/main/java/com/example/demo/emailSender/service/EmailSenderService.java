package com.example.demo.emailSender.service;

import com.example.demo.emailSender.mapper.EmailSenderMapper;
import com.example.demo.member.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author LHS
 * @version 1.0.0
 * @since 2022-06-22 오후 6:06
 */
@Service
public class EmailSenderService {

    @Autowired
    private EmailSenderMapper emailSenderMapper;

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(String toEmail,
                          String subject,
                          String body
                          ) throws MessagingException{
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom("hs95blue@gmail.com");
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);

//        mailSender.send(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("kabsung1@gmail.com"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject(subject); //메일제목
        helper.setText(body, true); //ture넣을경우 html

        mailSender.send(mimeMessage);

        System.out.println("Mail Send...");
    }

    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("hs95blue@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
        mailSender.send(mimeMessage);
        System.out.println("Mail Send...");
    }


    public MemberVO getUserVO(String email) {
        return emailSenderMapper.getUserVOfromEmail(email);
    }

    public MemberVO getUserVO(int id) {
        return emailSenderMapper.getUserVOfromId(id);
    }

    public int joinCertified(int id) {
        return emailSenderMapper.updateCertified(id);
    }

    public int resetPassword(int id) {
        return emailSenderMapper.resetPassword(id);
    }
}
