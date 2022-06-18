package com.example.demo.file.controller;

import com.example.demo.common.vo.Message;
import com.example.demo.file.service.FileService;
import com.example.demo.file.service.impl.FIleServiceImpl;
import com.example.demo.file.vo.FileVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

/**
 * Created by Hyunsik Lee on 2022-05-28. Blog : https://hs95blue.github.io/ Github :
 * https://github.com/hs95blue
 */
@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  FIleServiceImpl fileService;

  @Value("${spring.servlet.multipart.location}")
  String tmpPath;

  @PostMapping("/upload")
  public ResponseEntity<Message> saveFile(@RequestParam MultipartFile[] file) throws IOException {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;

    try {
      // 플젝저장 쿼리 서비스
      List<FileVO> uploadList = fileService.upload(file);
      message.setStatus(200);
      message.setMessage("업로드 성공");
      message.getData().put("uploadList",uploadList); // 조회시 보낼 데이터 이렇게 넣어주세요
    } catch (Exception e) {
      System.out.println(e.getMessage());
      message.setStatus(500);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message.setMessage("업로드에 실패하였습니다.");
    }
    return new ResponseEntity<>(message, headers, status);
  }

  // 이미지 display
  @GetMapping("/images/{uuid}")
  public ResponseEntity<Resource> showImage(@PathVariable String uuid, HttpServletResponse response) throws
      MalformedURLException,IOException {
    FileVO imageInfo = fileService.findById(uuid);
    String storedFileName = tmpPath + imageInfo.getFilePath() + imageInfo.getUuid() + "_" + imageInfo.getOriName();
    HttpHeaders header = new HttpHeaders();
    Resource resource = new FileSystemResource(storedFileName);
    //파일 경로
    Path saveFilePath = Paths.get(storedFileName);

    //해당 경로에 파일이 없으면
    if(!saveFilePath.toFile().exists()) {
      throw new RuntimeException("file not found");
    }

    header.add("Content-type", Files.probeContentType(saveFilePath));


    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
  }

  // 썸네일
  @GetMapping("/images/thumbnail/{uuid}")
  public ResponseEntity<Resource> showThumbnailImage(@PathVariable String uuid) throws
      MalformedURLException, IOException {
    FileVO imageInfo = fileService.findById(uuid);
    String storedFileName = tmpPath + imageInfo.getThumbnailPath() + "s_" + imageInfo.getUuid() + "_" + imageInfo.getOriName();
    //파일 경로
    Path saveFilePath = Paths.get(storedFileName);

    HttpHeaders header = new HttpHeaders();
    Resource resource = new FileSystemResource(storedFileName);

    //해당 경로에 파일이 없으면
    if(!saveFilePath.toFile().exists()) {
      throw new RuntimeException("file not found");
    }
    header.add("Content-type", Files.probeContentType(saveFilePath));


    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
  }

  @GetMapping("/attach/{uuid}")
  public ResponseEntity<Resource> downloadAttach(HttpServletResponse res, @PathVariable String uuid) throws MalformedURLException {
    //...itemId 이용해서 고객이 업로드한 파일 이름인 uploadFileName랑 서버 내부에서 사용하는 파일 이름인 storeFileName을 얻는다는 내용은 생략
    FileVO file = fileService.findById(uuid);
    String storedFileName = tmpPath + file.getFilePath() + file.getUuid()+"_"+file.getOriName();
    String uploadFileName = file.getOriName();
    //파일 경로
    Path saveFilePath = Paths.get(storedFileName);

    //해당 경로에 파일이 없으면
    if(!saveFilePath.toFile().exists()) {
      throw new RuntimeException("file not found");
    }

    UrlResource resource = new UrlResource("file:" + saveFilePath);

    //한글 파일 이름이나 특수 문자의 경우 깨질 수 있으니 인코딩 한번 해주기
    String encodedUploadFileName = UriUtils.encode(uploadFileName,
        StandardCharsets.UTF_8);

    //아래 문자를 ResponseHeader에 넣어줘야 한다. 그래야 링크를 눌렀을 때 다운이 된다.
    //정해진 규칙이다.
    String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
  }

  @PostMapping("/test")
  public String test(FileVO fileVO, MultipartFile file) {

    System.out.println(fileVO.toString());
    System.out.println(file.getOriginalFilename());

    return "따봉!";
  }
}
