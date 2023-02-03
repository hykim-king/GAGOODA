package com.example.gagooda_project.dto;

import java.util.Date;

public class UserDto {
    private int userId;  // PK user_id NN
    private String email; // email NN
    private String pw; // pw NN
    private String name;  // name NN
    private String nickname;  // nickname NN
    private String phone;  // phone NN
    private byte emailcheck; // emailcheck NN DEFAULT 0
    private Date regDate; // reg_date NN
    private Date redId; // reg_id NN
    private Date modifyDate;  // modify_date NN
    private String modifyId; // modify_id NN?
    private String cardName;  // card_name
    private int cardNum;  // card_num
    private int cvc;  // cvc
    private int cardYear; // card_year
    private int cardMonth; // card_month
    private String gMst; // g_mst NN (COMMON_CODE : USER) 1:N
    private String gDet; // g_det NN (COMMON_CODE : USER) 1:N
    private String msMst; // ms_mst NN (COMMON_CODE : USER) 1:N
    private String msDet; // ms_det NN (COMMON_CODE : USER) 1:N
}
