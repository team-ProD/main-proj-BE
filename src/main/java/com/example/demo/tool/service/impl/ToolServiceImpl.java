package com.example.demo.tool.service.impl;

import com.example.demo.tool.mapper.ToolMapper;
import com.example.demo.tool.service.ToolService;
import com.example.demo.tool.vo.ToolVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hyunsik Lee on 2022-06-04. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Service
public class ToolServiceImpl implements ToolService {

  @Autowired
  ToolMapper toolMapper;

  @Override
  public List<ToolVO> getToolList(ToolVO toolVO) {
    return toolMapper.getToolList(toolVO);
  }

  @Override
  public List<ToolVO> getToolListWithMaster(ToolVO toolVO) {
    return toolMapper.getToolListWithMaster(toolVO);
  }

  @Override
  public List<ToolVO> getToolListWithAll(ToolVO toolVO) {
    return toolMapper.getToolListWithAll(toolVO);
  }

  @Override
  public List<ToolVO> getToolListWithUsed(ToolVO toolVO) {
    return toolMapper.getToolListWithUsed(toolVO);
  }

  @Override
  public List<ToolVO> getToolListWithFree(ToolVO toolVO) {
    return toolMapper.getToolListWithFree(toolVO);
  }
  @Override
  public List<ToolVO> getUsingTool(ToolVO toolVO) {
    return toolMapper.getUsingTool(toolVO);
  }
  @Override
  public int updateProjectTool(ToolVO toolVO) {
    return toolMapper.updateProjectTool(toolVO);
  }


}
