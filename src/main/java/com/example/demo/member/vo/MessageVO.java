package com.example.demo.member.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class MessageVO {
  private HttpStatus status;
  private String message;
  private HashMap<String,Object> data;

  public MessageVO() {
    this.status = HttpStatus.BAD_REQUEST;
    this.data = new HashMap<String, Object>();
    this.message = null;
  }
}
