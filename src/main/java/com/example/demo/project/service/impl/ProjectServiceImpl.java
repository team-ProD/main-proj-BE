package com.example.demo.project.service.impl;

import com.example.demo.project.mapper.ProjectMapper;
import com.example.demo.project.service.ProjectService;
import com.example.demo.project.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

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
    public void createProject(ProjectVO projectVO) {
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
