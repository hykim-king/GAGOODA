package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RefundDto {
    private int refundId;  // 환불 ID, PK, NN
    private int userId;  // 사용자 ID, FK, NN, N:1
    private int orderDetailId;  // 주문 상세 ID, FK, NN, 1:1
    private String rfMst;  // 환불처리상태_MST, FK, NN, N:1
    private String rfDet;  // 환불처리상태_DET, FK, NN, N:1
    private String rfrMst;  // 환불/교환 사유_MST, FK, NN, N:1
    private String rfrDet;  // 환불/교환 사유_DET, FK, NN, N:1
    private String reason;  // 환불 사유, NN
    private int count;  // 환불 수량, NN
    private Date date;  // 환불 일자, NN
    private boolean status;  // 환불 상태, NN
    private String imgCode;  // 이미지 코드, NULL
}
