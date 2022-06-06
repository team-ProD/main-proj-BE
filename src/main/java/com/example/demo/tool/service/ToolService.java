package com.example.demo.tool.service;

import com.example.demo.tool.vo.ToolVO;
import java.util.List;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
public interface ToolService {
  List<ToolVO> getToolList(ToolVO toolVO);

}
