package com.example.gagooda_project.dto;

import java.util.Date;

public class UserDto {
    int userId;  // PK user_id NN
    String email; // email NN
    String pw; // pw NN
    String name;  // name NN
    String nickname;  // nickname NN
    String phone;  // phone NN
    byte emailcheck; // emailcheck NN DEFAULT 0
    Date regDate; // reg_date NN
    Date redId; // reg_id NN
    Date modifyDate;  // modify_date NN
    String modifyId; // modify_id NN?
    String cardName;  // card_name
    int cardNum;  // card_num
    int cvc;  // cvc
    int cardYear; // card_year
    int cardMonth; // card_month
    String gMst; // g_mst NN (COMMON_CODE : USER) 1:N
    String gDet; // g_det NN (COMMON_CODE : USER) 1:N
    String msMst; // ms_mst NN (COMMON_CODE : USER) 1:N
    String msDet; // ms_det NN (COMMON_CODE : USER) 1:N
}
