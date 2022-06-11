package com.example.demo.security.vo;

/**
 * Created by Hyunsik Lee on 2022-06-10. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */

import com.example.demo.member.vo.MemberVO;
import java.io.Serializable;
import lombok.Getter;

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
