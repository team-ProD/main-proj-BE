package com.example.demo.file.service;

import com.example.demo.file.vo.FileVO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
public interface FileService {

  List<FileVO> getFileList(FileVO fileVO);

  List<FileVO> upload(MultipartFile[] file, String path);

  List<FileVO> upload(MultipartFile[] file);

  boolean saveThumbnail(File image,String fileName, String path, String uuid)
      throws IOException;

  FileVO findById(String uuid);
}
