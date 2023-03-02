package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommunityDto {
    private int commId; //PK NOT NUL AUTO_INCREMENT
    private int userId; //FK
    private String content;
    private Date regDate; //NOT NULL DEFAULT CURRENT_TIMESTAMP
    private String imgCode;
    private int views; // NOT NULL DEFAULT 0
}
