package com.example.demo.tool.vo;

import com.example.demo.common.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ToolVO extends CommonVO {

  private String id;
  private String name;
  private String imgPath;
  private String projectid;

}
