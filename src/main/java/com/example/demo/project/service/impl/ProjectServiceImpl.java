package com.example.demo.project.service.impl;

import com.example.demo.file.service.impl.FIleServiceImpl;
import com.example.demo.file.vo.FileVO;
import com.example.demo.member.vo.MemberVO;
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

    /**
     * 초대 수락
     * @param projectVO
     */
    @Override
    public void attendantAccept(ProjectVO projectVO) {
        projectMapper.attendantAccept(projectVO);
    }

    /**
     * 초대 거절
     * @param projectVO
     */
    @Override
    public void attendantDeny(ProjectVO projectVO) {
        projectMapper.attendantDeny(projectVO);
    }

    /**
     * 사용자 별 프로젝트 조회 (참여중, 참여대기중 등)
     * @param projectVO
     */
    @Override
    public  List<ProjectVO>  getProjectByMemberId(ProjectVO projectVO) {
        return projectMapper.getProjectByMemberId(projectVO);
    }

    /**
     * 사용자 별 프로젝트 조회 (참여중, 참여대기중 등)
     * @param projectVO
     */
    @Override
    public int getProjectCntInvited(ProjectVO projectVO) {
        return projectMapper.getProjectCntInvited(projectVO);
    }

    /**
     * 프로젝트 하위 > 팀 생성
     */
    public int addNewTeam(ProjectVO projectVO) {
        return projectMapper.addNewTeam(projectVO);
    }


    /**
     * 초대된 멤버리스트 노출
     */
    public List<MemberVO> getMembersInvited(ProjectVO projectVO){
        return projectMapper.getMembersInvited(projectVO);
    }

     /**
     * 사용중인 협업툴 링크 조회
     */
     public List<ProjectVO> getToolInfoByProjectId(ProjectVO projectVO){
        return projectMapper.getToolInfoByProjectId(projectVO);
    }

    /**
     * 사용할 협업툴 등록
     */
    public int addNewTool(ProjectVO projectVO){
        return projectMapper.addNewTool(projectVO);
    }

    /**
     * 프로젝트 내에 팀 정보 조회
     */
    public List<ProjectVO> getTeamInfo(ProjectVO projectVO){
        return projectMapper.getTeamInfo(projectVO);
    }

    /**
     * 팀구성원 추가
     */
    public int updateProfileWithTeam(ProjectVO projectVO){
        return projectMapper.updateProfileWithTeam(projectVO);
    }
}
