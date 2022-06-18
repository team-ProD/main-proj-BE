package com.example.demo.security.vo;

import com.example.demo.member.vo.MemberVO;
import java.util.Collections;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Hyunsik Lee on 2022-06-10. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */

@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey, name, email;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
    return ofGoogle(userNameAttributeName, attributes);
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public UserVO toEntity() {
    return UserVO.builder()
        .name(name)
        .email(email)
        .roles(Collections.singletonList("ROLE_USER"))
        .role("ROLE_USER")
        .build();
  }
}