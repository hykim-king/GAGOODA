package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PInquiryCommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PInquiryCommentMapper {
    PInquiryCommentDto findById(int id);
    int insertOne(PInquiryCommentDto pInquiryComment);
    int updateOne(PInquiryCommentDto pInquiryComment);
    int deleteOne(int id);
}
