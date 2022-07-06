package com.example.demo.emailSender.service;

import com.example.demo.emailSender.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    PasswordEncoder passwordEncoder; // RequiredConstructor 써도 되는데 다른 방식도 보여주고 싶었습니당.

    @Autowired
    EmailMapper emailMapper;

    public int chgPassword(String afterPassword) {
        afterPassword = passwordEncoder.encode(afterPassword);

        return emailMapper.chgPassword(afterPassword);
    }
}
