package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

// OrderDto 써야함
@Data
public class DeliveryDto {
    private String orderId; // 배송 ID PK NN AUTO_INCREMENT order:delivery_id = 1:1
    private int userId;  // 유저 ID (1:N)  NN
    private String userName;
    private String userEmail;
    private String userPhone;
    private int invoice; // 운송장 번호 11자리
    private Date startDate; // 배송 시작일
    private Date endDate; // 배송 완료일
    private String request; // 배송 요청사항
    private String dDet; // 배송 상태 상세 코드(공통 코드) NN FK (1:1)
    private OrderDto orderDto;



}
