package com.example.demo.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserVO implements UserDetails, OAuth2User {

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

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }


    @Override
    public String getUsername() {
        return email;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    //계정 장금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        //계정 장금되었는지 확인하는 로직
        return true; // true -> 장금되지 않았음
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        //계정이 사용 가능한지 확인하는 롤직
        return true; // true -> 사용 가능
    }
}
