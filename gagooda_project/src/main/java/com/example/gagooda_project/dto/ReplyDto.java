package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyDto {
    private int replyId; //PK AUTO_INCREMENT
    private String content;//DEFAULT ""
    private Date regDate; //DEFAULT CURRENT_TIMESTAMP
    private int commId; //FK NN
    private int userId; //FK NN
    private int fk_reply_id; //self-join
}
