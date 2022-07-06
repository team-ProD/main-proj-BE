package com.example.demo.project.vo;

import com.example.demo.common.vo.CommonVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class ProjectVO extends CommonVO {
    private int id;
    private String logoName;
    private String logoPath;
    private String summary;
    private int attendantNumber;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date modifyDate;

    private int profileId;
    private Arrays members; // 프로젝트 하위 > 팀 구성원 정보(프로필 아이디 값.)

    private List<ArrayData> arrayData;

    @Setter
    @Getter
    public static class ArrayData{
        private int id;
        private int projectId;
        private int toolId;
        private String link;
        private String status;
    }
}