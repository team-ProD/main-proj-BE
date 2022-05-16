package com.example.demo.member.service.impl;

import com.example.demo.member.mapper.UserMapper;
import com.example.demo.member.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Hyunsik Lee on 2022-05-08. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailService {

  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userMapper.findByEmail(username);
  }
}