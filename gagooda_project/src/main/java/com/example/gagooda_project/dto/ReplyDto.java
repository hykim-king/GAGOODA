package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReplyDto {
    private int replyId; //PK AUTO_INCREMENT
    private String content;//DEFAULT ""
    private Date regDate; //DEFAULT CURRENT_TIMESTAMP
    private int commId; //FK NN
    private int userId; //FK NN
    private Integer fkReplyId; //self-join
    private UserDto user;
    private List<ReplyDto> replyList; // reply(replyNo) : reReply(fk_reply_id) = 1:N
}
