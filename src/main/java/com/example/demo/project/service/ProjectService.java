package com.example.demo.project.service;

import com.example.demo.project.vo.ProjectVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    /**
     * 프로젝트 조회
     */
    ProjectVO selectProject(ProjectVO projectVO);

    /**
     * 프로젝트 생성
     */
    void createProject(ProjectVO projectVO, List<MultipartFile> files);

    /**
     * 프로젝트 수정
     */
    void updateProject(ProjectVO projectVO);

    /**
     * 사용자 초대
     * @param projectVO
     */
    void userAttendant(ProjectVO projectVO);

    /**
     * 초대 수락
     * @param projectVO
     */
    void attendantAccept(ProjectVO projectVO);
}
