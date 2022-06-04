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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class FileController {

  @Autowired
  FIleServiceImpl fileService;



  @PostMapping("/upload")
  public ResponseEntity<Message> saveFile(@RequestParam String itemName, @RequestParam MultipartFile[] file) throws IOException {
    Message message = new Message();
    HttpHeaders headers= new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    HttpStatus status = HttpStatus.OK;

    try {
      for (MultipartFile multipartFile : file) {
        FileVO vo = new FileVO(UUID.randomUUID().toString(),multipartFile.getOriginalFilename(),multipartFile.getContentType());
        int uploadCnt = fileService.upload(vo);
        File newFileName = new File(vo.getUuid() + "_" + vo.getUuid());
        multipartFile.transferTo(newFileName);
      }
      message.setStatus(200);
      message.setMessage("업로드 성공");
     // message.getData().put("toolList",list); // 조회시 보낼 데이터 이렇게 넣어주세요
      // message.setData(); 데이터 넣을게 없음..
    } catch (Exception e) {
      System.out.println(e.getMessage());
      message.setStatus(500);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      message.setMessage("업로드에 실패하였습니다.");
    }
    return new ResponseEntity<>(message, headers, status);
  }

  @GetMapping("/images/{filename}")
  public Resource showImage(@PathVariable String filename) throws
      MalformedURLException {
    String storedFileName = "/images/" + filename;
    //파일 경로
    Path saveFilePath = Paths.get(storedFileName);

    //해당 경로에 파일이 없으면
    if(!saveFilePath.toFile().exists()) {
      throw new RuntimeException("file not found");
    }
    return new UrlResource("file:" + saveFilePath);
  }

  @GetMapping("/attach/{itemId}")
  public ResponseEntity<Resource> downloadAttach(HttpServletResponse res, @PathVariable Long itemId) throws MalformedURLException {
    //...itemId 이용해서 고객이 업로드한 파일 이름인 uploadFileName랑 서버 내부에서 사용하는 파일 이름인 storeFileName을 얻는다는 내용은 생략
    FileVO file = fileService.findById(itemId);
    String storedFileName = file.getFilePath() + file.getUuid();
    String uploadFileName = file.getOriName();

    //파일 경로
    Path saveFilePath = Paths.get(storedFileName);

    //해당 경로에 파일이 없으면
    if(!saveFilePath.toFile().exists()) {
      throw new RuntimeException("file not found");
    }

 //   FileInputStream fis = null;

//    try {
//      fis = new FileInputStream(saveFilePath.toFile());
//      FileCopyUtils.copy(fis, res.getOutputStream());
//      res.getOutputStream().flush();
//    }
//    catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//    finally {
//      try {
//        fis.close();
//      }
//      catch (Exception e) {
//        e.printStackTrace();
//      }
//
//    }

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
}
