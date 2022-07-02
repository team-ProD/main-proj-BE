package com.example.demo.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberVO {


  private int id;
  private String email;
  private String password;
  private String name;
  @Builder.Default
  private List<String> roles = new ArrayList<>();
  private String role;
  private int privacyAgree;
  private int remoteLoginAgree;
  private String createDate;
  private String modifyDate;
  private int certified;


  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }


}
