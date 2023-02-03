package com.example.gagooda_project.dto;

import lombok.Data;


@Data
public class AddressDto {
    private int addressId;  //배송지 ID
    private int userId; // 사용자 ID
    private String addressName; // 배송지 이름
    private boolean addressDefault;  // 기본 배송지 true:기본배송지O, false:기본배송지X
    private String address1; // 우편번호
    private String address2; // 주소
    private String address3; // 상세주소
    private String receiverName; // 수령자 이름
    private String receiverPhone; // 수령자 전화번호(12자 제한)
}
