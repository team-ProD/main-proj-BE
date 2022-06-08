package com.example.demo.file.mapper;

import com.example.demo.file.vo.FileVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@Mapper
public interface FileMapper {

   List<FileVO> getFileList(FileVO fileVO);

   int upload(FileVO fileVO);

   FileVO findById(Long itemId);
}
