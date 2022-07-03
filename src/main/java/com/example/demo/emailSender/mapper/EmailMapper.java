package com.example.demo.emailSender.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

    int updateCertified(int id);

    int chgPassword(String afterPassword);

    int projectParticipated(int id);
}
