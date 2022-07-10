package com.example.demo.tool.service;

import com.example.demo.tool.vo.ToolVO;
import java.util.List;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
public interface ToolService {
  List<ToolVO> getToolList(ToolVO toolVO);
  List<ToolVO> getToolListWithMaster(ToolVO toolVO);
  List<ToolVO> getToolListWithAll(ToolVO toolVO);
  List<ToolVO> getToolListWithUsed(ToolVO toolVO);
  List<ToolVO> getToolListWithFree(ToolVO toolVO);
  List<ToolVO> getUsingTool(ToolVO toolVO);
  int updateProjectTool(ToolVO toolVO);

}
