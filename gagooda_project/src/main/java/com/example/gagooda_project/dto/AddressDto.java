package com.example.gagooda_project.dto;

import lombok.Data;


@Data
public class AddressDto {
    private int addressId;        // address_id | 배송지 ID | PK | NN
    private int userId;           // user_id | 유저 ID | FK | NN | (USER:ADDRESS) 1:N
    private String aname;          // aname | 배송지 이름
    private boolean home;      // home | 기본 배송지 여부 | tinyint
    private String postCode;      // post_code | 우편번호 | NN
    private String address;       // address | 주소 | NN
    private String addressDetail; // address_detail | 상세주소 | NN
    private String receiverName;  // receiver_name | 수령자 이름 | NN
    private String receiverPhone; // receiver_phone | 수령자 전화번호(11자 제한)
    private boolean elevator;     // elevator | 엘레베이터 유무 | tinyint
}
