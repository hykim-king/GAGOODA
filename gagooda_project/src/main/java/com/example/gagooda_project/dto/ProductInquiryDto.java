package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProductInquiryDto {
    // 기본
    private int pInquiryId; // p_inquiry_id NN
    private int userId; // user_id FK NN (USER:PRODUCT_INQUIRY) 1:N
    private String optionCode; // option_code FK NN (OPTION_PRODUCT:PRODUCT_INQUIRY) 1:N
    private String productCode; // product_code FK NN (PRODUCT:PRODUCT_INQUIRY) 1:N
    private String title; // NN
    private String content; // NN
    private String reply;
    private boolean secret; // NN
    private Date regDate; // NN
    private Date replyDate; // NN
    private int replyId; // NN
    private String piDet; // NN

    // 추가
    private UserDto user;
    private OptionProductDto optionProductDto;
    private CommonCodeDto commonCodeDto;
    private List<OptionProductDto> optionProductList;
}