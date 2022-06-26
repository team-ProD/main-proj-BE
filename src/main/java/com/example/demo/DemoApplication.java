package com.example.demo;

import com.example.demo.email.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DemoApplication {

  @Autowired
  private EmailSenderService service;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }


  @EventListener(ApplicationReadyEvent.class)
  public void triggerMail(){
    service.sendEmail(
            "hs95blue@gmail.com",
            "어플리케이션이 실행되었습니다.",
            "어플리케이션이 실행되었습니다."
    );
  }
}
