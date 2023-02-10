package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

// OrderDto 써야함
@Data
public class DeliveryDto {
    private int delivery_id; // 배송 ID PK NN AUTO_INCREMENT order:delivery_id = 1:1
    private int order_id; // 주문 ID(1:1) FK NN
    private int invoice; // 운송장 번호 11자리
    private Date start_date; // 배송 시작일
    private Date end_date; // 배송 완료일
    private String request; // 배송 요청사항
    private String d_det; // 배송 상태 상세 코드(공통 코드) NN


}
