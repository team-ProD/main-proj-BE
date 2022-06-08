package com.example.demo.project.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ProjectVO {
    private int id;
    private String name;
    private String logoName;
    private String logoPath;
    private String summary;
    private int attendantNumber;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date modifyDate;

    private int profileId;
}