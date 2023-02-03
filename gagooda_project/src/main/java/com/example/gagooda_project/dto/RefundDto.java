package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RefundDto {
    private int refundId;  // 환불 ID
    private int userId;  // 사용자 ID
    private int orderDetailId;  // 주문 상세 ID
    private String rfMst;  // 환불처리상태_MST
    private String rfDet;  // 환불처리상태_DET
    private String rfrMst;  // 환불/교환 사유_MST
    private String rfrDet;  // 환불/교환 사유_DET
    private String reason;  // 환불 사유
    private int count;  // 환불 수량
    private Date date;  // 환불 일자
    private int status;  // 환불 상태
    private String imgCode;  // 이미지 코드
}
