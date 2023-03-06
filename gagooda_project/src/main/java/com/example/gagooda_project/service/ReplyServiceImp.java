package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import com.example.gagooda_project.mapper.ReplyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImp implements ReplyService{
    private ReplyMapper replyMapper;
    private Logger log= LoggerFactory.getLogger(this.getClass());

    public ReplyServiceImp(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
    }

    @Override
    public List<ReplyDto> commDetailList(int commId, PagingDto paging) {
        log.info("service에 진입했습니다.");
        int totalRows =replyMapper.countByCommId(commId) ;
        log.info("totalRows: "+totalRows);
        paging.setOrderField("reg_date");
        paging.setTotalRows(totalRows);
        return replyMapper.findByCommIdPaging(commId, paging);
    }

    @Override
    public List<ReplyDto> userDetailList(int userId, PagingDto paging) {
        return null;
    }

    @Override
    public int removeOne(int replyId) {
        return replyMapper.deleteById(replyId);
    }

    @Override
    public int modifyOne(ReplyDto reply) {
        return replyMapper.updateById(reply);
    }

    @Override
    public int registerOne(ReplyDto reply) {
        return replyMapper.insert(reply);
    }

    @Override
    public ReplyDto detail(int replyId) {

        return replyMapper.findById(replyId);
    }

    @Override
    public int countReply(int commId) {
        return replyMapper.countByCommId(commId);
    }
}
