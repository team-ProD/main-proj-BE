package com.example.demo.file.service.impl;

import com.example.demo.file.mapper.FileMapper;
import com.example.demo.file.service.FileService;
import com.example.demo.file.vo.FileVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Service
public class FIleServiceImpl implements FileService {

  @Autowired
  FileMapper fileMapper;

  @Override
  public List<FileVO> getFileList(FileVO fileVO) {
    return fileMapper.getFileList(fileVO);
  }

  @Override
  public int upload(FileVO fileVO) {
    return fileMapper.upload(fileVO);
  }


  @Override
  public FileVO findById(Long itemId) {
    return fileMapper.findById(itemId);
  }
}
