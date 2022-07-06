package com.example.demo;

import com.example.demo.emailSender.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

  @Autowired
  private EmailSenderService service;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

/*
  @EventListener(ApplicationReadyEvent.class)
  public void triggerMail() throws MessagingException {
    service.sendEmail(
            "kabsung3@naver.com",
            "어플리케이션이 실행되었습니다.",
            "어플리케이션이 실행되었습니다."
    );
  }
*/
}
