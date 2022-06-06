package com.example.demo.common.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
public class Message {

    private int status;
    private String message;
    private HashMap<String, Object> data;

    public Message() {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.data = new HashMap<String, Object>();
        this.message = null;
    }

}
