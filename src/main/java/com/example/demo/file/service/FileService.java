package com.example.demo.file.service;

import com.example.demo.file.vo.FileVO;
import java.util.List;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
public interface FileService {

  List<FileVO> getFileList(FileVO fileVO);

  int upload(FileVO fileVO);


  FileVO findById(Long itemId);
}
