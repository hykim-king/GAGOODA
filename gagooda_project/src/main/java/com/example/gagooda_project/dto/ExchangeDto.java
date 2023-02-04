package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeDto {
    private int exchangeId;  // 교환 ID, PK, NN
    private int userId;  // 사용자 ID, FK, NN, exchange:user = N:1
    private int orderDetailId;  // 주문 상세 ID, FK, exchange:order_detail = NN, 1:1
    private String productId;  // 상품 ID, FK, NN, exchange:product = 1:1
    private String exMst;  // 교환처리상태_MST, FK, NN, exchange:common_code = N:1
    private String exDet;  // 교환처리상태_DET, FK, NN, exchange:common_code = N:1
    private String rfrMst;  // 환불/교환 사유_MST, FK, NN, exchange:common_code = N:1
    private String rfrDet;  // 환불/교환 사유_DET, FK, NN, exchange:common_code = N:1
    private String exchaReason;  // 교환 사유, NN
    private int exchaCount;  // 교환 수량, NN
    private Date exchaDate;  // 교환일, NN
    private boolean exchaState;  // 교환 상태, NN
}
