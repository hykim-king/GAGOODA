package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PInquiryCommentDto {
    private int pInquiryId;  // 상품 문의 답변 아이디 p_inquiry_Id PK&FK NN ( PInquiryComment : ProductInquiry ) 1:1
    private String content;  // 내용 content  NN
    private Date regDate;    // 등록일 reg_date NN
    private int regId;       // 등록자 reg_id   NN
    private Date modDate;    // 수정일 mod_date NN
    private int modId;       // 수정자 mod_id   NN

}
