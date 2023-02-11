package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductInquiryDto {
    private int pInquiryId; // p_inquiry_id NN
    private int userId; // user_id FK NN (USER:PRODUCT_INQUIRY) 1:N
    private String optionCode; // option_code FK NN (OPTION_PRODUCT:PRODUCT_INQUIRY) 1:N
    private String productCode; // product_code FK NN (PRODUCT:PRODUCT_INQUIRY) 1:N
    private String title; // NN
    private String content; // NN
    private boolean secret; // NN
    private boolean status; // NN
    private Date regDate; // NN
    private Date modDate; // NN
    private String piDet; // NN
}
