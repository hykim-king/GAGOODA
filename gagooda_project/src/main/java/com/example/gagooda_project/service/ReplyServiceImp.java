package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import com.example.gagooda_project.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImp implements ReplyService{
    private ReplyMapper replyMapper;

    @Override
    public List<ReplyDto> commDetailList(int commId, PagingDto paging) {
        return null;
    }

    @Override
    public List<ReplyDto> userDetailList(int userId, PagingDto paging) {
        return null;
    }

    @Override
    public int removeOne(int replyId) {
        return 0;
    }

    @Override
    public int modifyOne(ReplyDto reply) {
        return 0;
    }

    @Override
    public int registerOne(ReplyDto reply) {
        return replyMapper.insert(reply);
    }

    @Override
    public ReplyDto detail(int replyId) {
        return null;
    }
}
