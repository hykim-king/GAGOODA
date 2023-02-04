package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

// OrderDto 써야함
@Data
public class DeliveryDto {
    private int deliveryId; // delivery_id  PK  NN  배송ID
    private int orderId; // order_id  FK  NN  (주문:배송) 1:1  주문ID
    private String dDet; // d_det  NN  배송 상태_det(공통 코드)  DEFAULT 'd0'
    private int invoice; // invoice  운송장 번호(11자리)
    private Date startDate; // start_date 배송 시작일
    private Date endDate; // end_date 배송 완료일
    private String request; // request 배송 요청사항

}
