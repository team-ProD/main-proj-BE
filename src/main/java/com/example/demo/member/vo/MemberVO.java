package com.example.demo.member.vo;

import com.example.demo.common.oauth.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberVO {
    private String name;
    private String email;
    private Role role;
    private String password;
    private String picture;
    private String privacyAgree;
    private String remoteLoginAgree;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @Builder
    public MemberVO(String name, String email, Role role, String picture) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.picture = picture;
    }

    public MemberVO update(String name) {
        this.name = name;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
