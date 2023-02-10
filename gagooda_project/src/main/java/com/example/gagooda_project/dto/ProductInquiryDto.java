package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProductInquiryDto {
    private String piDet;
    private String status;
    private Date regDate;
    private String content;
    private String secret;
    private String optionId;
    private String productId;
    private int pInquiryId;
    private int userId;
    private String title;
    private Date modDate;
}
