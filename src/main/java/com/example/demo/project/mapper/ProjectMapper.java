package com.example.demo.project.mapper;

import com.example.demo.member.vo.MemberVO;
import com.example.demo.project.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 초대 거절
     * @param projectVO
     */
    void attendantDeny(ProjectVO projectVO);

    /**
     * 사용자 별 프로젝트 조회 (참여중, 참여대기중 등)
     * @param projectVO
     */
    List<ProjectVO> getProjectByMemberId(ProjectVO projectVO);

    /**
     * 초대받은 프로젝트 갯수 체크
     * @param projectVO
     */
    int getProjectCntInvited(ProjectVO projectVO);

    /**
     * 프로젝트 하위 > 팀 생성
     */
    int addNewTeam(ProjectVO projectVO);

    /**
     * 초대된 멤버리스트 노출
     */
    List<MemberVO> getMembersInvited(ProjectVO projectVO);

    /**
     * 사용중인 협업툴 링크 조회
     */
    List<ProjectVO> getToolInfoByProjectId(ProjectVO projectVO);

    /**
     * 프로젝트 내에 팀 정보 조회
     */
    List<ProjectVO> getTeamInfo(ProjectVO projectVO);


    /**
     * 사용할 협업툴 등록
     */
    int addNewTool(ProjectVO projectVO);

    /**
     * 팀구성원 추가
     */
    int updateProfileWithTeam(ProjectVO projectVO);

    /**
     * 프로젝트 룸 생성
     */
     int createProjectRoom(ProjectVO projectVO);

    /**
     * 프로젝트 룸 조회
     */
    List<ProjectVO> getProjectRoom(ProjectVO projectVO);

    /**
     * 프로젝트 룸 수정
     */
    int updateProjectRoom(ProjectVO projectVO);

    /**
     * 프로젝트 룸 삭제
     */
    int delProjectRoom(ProjectVO projectVO);

}
