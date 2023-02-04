package com.example.gagooda_project.dto;

import lombok.Data;


@Data
public class AddressDto {
    private int addressId;  // address_id  PK  NN  배송ID
    private int userId; // user_id  FK  NN  (USER : ADDRESS) 1 : N  유저ID
    private String addressName; // address_name NULL  배송지 이름
    private boolean addressDefault;  // address_default  NN  true(1):기본 배송지, false(0):그냥 배송지
    private String post_code; // post_code  NN  우편번호
    private String address; // address NN  주소
    private String addressDetail; // address_detail  NN 상세주소
    private String receiverName; // receiver_name  NN  수령자 이름
    private String receiverPhone; //  receiver_phone  NN  수령자 전화번호(12자 제한)
}
