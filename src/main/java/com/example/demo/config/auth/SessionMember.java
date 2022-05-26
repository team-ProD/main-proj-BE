package com.example.demo.config.auth;

import com.example.demo.member.vo.MemberVO;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 저장하려면 직렬화를 해야하는데
 * MemberVO 엔티티는 추후 변경사항이 있을 수 있기 때문에
 * 직렬화를 하기 위한 별도의 SessionMemver
 */
@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionMember(MemberVO memberVO) {
        this.name = memberVO.getName();
        this.email = memberVO.getEmail();
    }

}
