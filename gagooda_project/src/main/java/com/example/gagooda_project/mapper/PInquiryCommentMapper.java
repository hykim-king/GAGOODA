package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PInquiryCommentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PInquiryCommentMapper {
    int findByInquiryId(int pInquiryId);
    int insertOne(PInquiryCommentDto pInquiryComment);
    int updateOne(PInquiryCommentDto pInquiryComment);
    int deleteOne(int pInquiryId);
}
