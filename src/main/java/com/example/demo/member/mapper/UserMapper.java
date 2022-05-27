package com.example.demo.member.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public interface UserMapper {

    int save(Map user);

    HashMap findByEmail(String email);
}
