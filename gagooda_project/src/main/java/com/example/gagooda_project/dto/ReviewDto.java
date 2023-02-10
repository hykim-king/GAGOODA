package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDto {
    private int reviewId;       // review_id, PK, NN
    private int userId;         // user_id, FK, NN, ( REVIEW : USER ) 1:1
    private int productId;      // product_id, FK , NN, ( REVIEW : PRODUCT ) 1:N
    private String optionId;    // option_id, FK, NN, ( PRODUCT : OPTION ) 1:N
    private String content;     // content, NN
    private String imgCode;     // img_code, NULL
    private double rate;        // rate, NN
    private Date regDate;       // reg_date, NN
}
