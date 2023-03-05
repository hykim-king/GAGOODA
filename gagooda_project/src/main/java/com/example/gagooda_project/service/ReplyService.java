package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {
    List<ReplyDto> commDetailList(int commId, PagingDto paging);
    List<ReplyDto> userDetailList(int userId, PagingDto paging);
    int removeOne(int replyId);
    int modifyOne(ReplyDto reply);
    int registerOne(ReplyDto reply);
    ReplyDto detail(int replyId);
}
