package com.example.demo.security.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Hyunsik Lee on 2022-06-10. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Getter
@RequiredArgsConstructor
public enum Role {
  GUEST("ROLE_GUEST", "손님"),
  USER("ROLE_USER", "일반 사용자");

  private final String key;
  private final String title;
}
