package com.example.demo.emailSender.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {
    int chgPassword(String afterPassword);

}
