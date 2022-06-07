package com.example.demo.file.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

  private int id;
  private int fId;

  private String filePath;
  private String uuid;
  private String oriName;
  private String ext;


  private long fileSize;
  private String createUser;
  private String modifyUser;
  private String domain;
  private String subDomain;
  private String thumbnailPath;
  private String contentType;
  private Date createDate;
  private Date modifyDate;




  public FileVO(String uuid, String oriName, String contentType, String filePath, long fileSize, String thumbnailPath ) {
    this.uuid = uuid;
    this.oriName = oriName;
    this.contentType = contentType;
    String ext = oriName.substring(oriName.lastIndexOf(".") + 1);
    this.ext = ext;
    this.fileSize = fileSize;
    this.filePath = filePath;
    this.thumbnailPath = thumbnailPath;
  }
}
