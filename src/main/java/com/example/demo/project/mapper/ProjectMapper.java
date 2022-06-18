package com.example.demo.project.mapper;

import com.example.demo.project.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {

    /**
     * 프로젝트 생성
     * @param projectVO
     */
    void createProject(ProjectVO projectVO);

    /**
     * 프로젝트 조회
     * @param projectVO
     * @return
     */
    ProjectVO selectProject(ProjectVO projectVO);

    /**
     * 프로젝트 정보 수정
     * @param projectVO
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
