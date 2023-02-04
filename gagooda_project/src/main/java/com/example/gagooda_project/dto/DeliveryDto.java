package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

// OrderDto 써야함
@Data
public class DeliveryDto {
    private int deliveryId; // 배송 ID
    private int orderId; // 주문 ID(1:1)
    private String dMst; // 배송 상태 마스터 코드(공통 코드)
    private String dDet; // 배송 상태 상세 코드(공통 코드)
    private int invoice; // 운송장 번호 11자리
    private Date startDate; // 배송 시작일
    private Date endDate; // 배송 완료일
    private String request; // 배송 요청사항

}
