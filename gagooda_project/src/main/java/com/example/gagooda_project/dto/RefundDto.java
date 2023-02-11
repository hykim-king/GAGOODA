package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RefundDto {
    private int refundId;           // 환불 ID, refund_id, PK, NN
    private int orderDetailId;      // 주문상세 ID, order_detail_id, !FK, NN, ( REFUND : ORDER_DTEAIL ) 1:1
    private int orderId;            // 주문 ID, order_id, !FK, NN ( REFUND : ORDER ) 1:1
    private int addressId;          // 배송지 ID, address_id, !FK, NN ( REFUND : ADDRESS ) 1:1
    private int cancelAmount;       // 취소 요청 금액, cancel_amount
    private String reason;          // 환불사유, reason, NN
    private String comment;         // 환불 답변 사유, comment, NN
    private Date regdate;           // 환불 요청 일자, reg_date, NN
    private Date modDate;           // 환불 상태 변경 일자, mod_date, NN
    private String postCode; // post_code NN
    private String address; // NN
    private String addressDetail; // address_detail NN
    private String receiverName; // receiver_name NN
    private String receiverPhone; // receiver_phone NN
    private boolean elevator; // NN
    private String imgCode;         // 이미지 코드, img_code, NN
    private String rfDet;           // 환불처리상태_DET, FK, NN, ( REFUND : COMMON_CODE ) N:1
    private String rfrDet;          // 환불/ 교환사유_DET, rfr_det, FK, NN, ( REFUND : COMMON_CODE ) N:1

}
