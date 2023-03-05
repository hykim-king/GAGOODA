package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {
    List<ReplyDto> findAll();
    List<ReplyDto> findByCommId(int commId);
    List<ReplyDto> findByCommIdPaging(int commId, PagingDto paging);
    int countByCommId(int commId, PagingDto paging);
    List<ReplyDto> findByUserIdPaging(int userId, PagingDto paging);
    int countByUserId(int userId, PagingDto paging);
    ReplyDto findById(int replyId);
    int deleteById(int replyId);
    int updateById(ReplyDto dto);
    int insert(ReplyDto dto);
    List<ReplyDto> findByFkReplyId(int replyId);

}
