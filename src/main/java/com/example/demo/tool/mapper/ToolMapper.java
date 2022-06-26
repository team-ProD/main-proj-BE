package com.example.demo.tool.mapper;

import com.example.demo.tool.vo.ToolVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Mapper
public interface ToolMapper {
  List<ToolVO> getToolList(ToolVO toolVO);
  List<ToolVO> getToolListWithMaster(ToolVO toolVO);
  List<ToolVO> getToolListWithAll(ToolVO toolVO);
  List<ToolVO> getToolListWithUsed(ToolVO toolVO);
  List<ToolVO> getToolListWithFree(ToolVO toolVO);

}
