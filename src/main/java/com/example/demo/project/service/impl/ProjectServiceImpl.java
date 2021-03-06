package com.example.demo.project.service.impl;

import com.example.demo.file.service.impl.FIleServiceImpl;
import com.example.demo.file.vo.FileVO;
import com.example.demo.project.mapper.ProjectMapper;
import com.example.demo.project.service.ProjectService;
import com.example.demo.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    FIleServiceImpl fileService;

    /**
     * 프로젝트 조회
     *
     * @param projectVO
     * @return
     */
    @Override
    public ProjectVO selectProject(ProjectVO projectVO) {
        return projectMapper.selectProject(projectVO);
    }

    /**
     * 프로젝트 생성
     *
     * @param projectVO
     */
    @Override
    public void createProject(ProjectVO projectVO, MultipartFile[] files) {


        List<FileVO> fileVOList = fileService.upload(files, "logo");
        System.out.println("fileVOList : " + fileVOList);

        projectMapper.createProject(projectVO);
    }

    /**
     * 프로젝트 수정
     *
     * @param projectVO
     */
    @Override
    public void updateProject(ProjectVO projectVO) {
        projectMapper.updateProject(projectVO);
    }

    /**
     * 사용자 초대
     *
     * @param projectVO
     */
    @Override
    public void userAttendant(ProjectVO projectVO) {
        projectMapper.userAttendant(projectVO);
    }

    @Override
    public void attendantAccept(ProjectVO projectVO) {
        projectMapper.attendantAccept(projectVO);
    }
}
