package com.example.demo.member.vo;

import java.util.Date;
import lombok.Data;

/**
 * Created by Hyunsik Lee on 2022-05-25. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Data
public class ProfileVO {

  private int id;
  private int memberId;
  private String nickname;
  private String position;
  private int positionLevel;
  private Date createDate;
  private Date modifyDate;

}
