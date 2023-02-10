package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Datag
public class UserDto {
    private int userId;//user_id NN 사용자ID
    private String email;// email NN 사용자 이메일
    private String pw; // pw NN 사용자 비밀번호
    private String name;  // name NN 사용자 이름
    private String nickname;  // nickname NN 사용자 닉네임
    private String phone;  // phone NN 사용자 전화번호
    private Boolean emailCheck; // email_check NN DEFAULT 0 이메일 수신 여부 플래그
    private Date regDate; // reg_date NN 사용자 등록일
    private Date modDate;  // mod_date NN 사용자 수정일
    private String cardName;  // card_name 카드사
    private String gDet;//g_det NN 유저 상태
    private int cardNum;  // card_num  카드번호
    private int cvc;  // cvc CVC
    private int cardYear; // card_year 유효기간(년)
    private int cardMonth; // card_month 유효기간(월)

}
