package com.example.demo.file.service.impl;

import com.example.demo.file.mapper.FileMapper;
import com.example.demo.file.service.FileService;
import com.example.demo.file.vo.FileVO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Service
@Log4j2
public class FIleServiceImpl implements FileService {

  @Autowired
  FileMapper fileMapper;

  @Value("${spring.servlet.multipart.location}")
  String tmpPath;

  @Override
  public List<FileVO> getFileList(FileVO fileVO) {
    return fileMapper.getFileList(fileVO);
  }

  @Override
  public List<FileVO> upload(MultipartFile[] file, String path) {
    List<FileVO> list = new ArrayList<>();
    try {
      String thumbnailPath = "";

      // 이미지일 경우 /images/
      for (MultipartFile multipartFile : file) {
        String fileName = multipartFile.getOriginalFilename();
        long fileSize = multipartFile.getSize();

        String uuid = UUID.randomUUID().toString();

        File newFileName = new File(tmpPath+path + uuid+"_"+fileName);
        if (multipartFile.getSize() != 0) {
          if (!newFileName.exists()) {
            if (newFileName.getParentFile().mkdirs()) {
              newFileName.createNewFile();
            }
          }
          multipartFile.transferTo(newFileName);

          if(multipartFile.getContentType().startsWith("image")){
            // if (path.contains("images")) {
            // 썸네일 가공 로직(파일 저장까지)
            if (saveThumbnail(newFileName, fileName, path.replace("images/","") + "thumbnail/", uuid)){
              // 썸네일 경로 >> path + /thumbnail
              thumbnailPath
                  = path.replace("images/","") + "thumbnail/";
            }
          }

          FileVO vo = new FileVO(uuid,fileName,multipartFile.getContentType() , path, fileSize, thumbnailPath);
          fileMapper.upload(vo);
          list.add(vo);
        }
      }
    } catch (Exception e) {
      log.info(e.getMessage());
    }

    return list;
  }

  @Override
  public List<FileVO> upload(MultipartFile[] file) {

    return upload(file, "/");
  }

  @Override
  public boolean saveThumbnail(File image, String fileName, String path, String uuid)
      throws IOException {
    //썸네일 가공 후 파일 저장 로직
    // 여기서부터 파일 저장로직. 썸네일은 앞에 s_를 붙인다
    try {
      File thumbnailFile = new File(tmpPath+path +"s_"+ uuid+"_"+fileName);
      if (!thumbnailFile.exists()) {
        if (thumbnailFile.getParentFile().mkdirs()) {
          thumbnailFile.createNewFile();
        }
      }
      Thumbnailator.createThumbnail(image, thumbnailFile,100,100);
      return true;
    } catch (Exception e) {
      log.info(e.getMessage());
    }

    return false;
  }

  @Override
  public FileVO findById(String uuid) {
    return fileMapper.findById(uuid);
  }
}
