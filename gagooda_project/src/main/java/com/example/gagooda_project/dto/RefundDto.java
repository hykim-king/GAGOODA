package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RefundDto {
    private int refundId;           // refund_id, PK, NN
    private int userId;             // user_id, FK, NN, refund:user = N:1
    private int orderId;            // order_id, FK, NN 1:1
    private int orderDetailId;      // order_detail_id, FK, NN, refund:order_detail = 1:1
    private int addressId;          // address_id, FK, NN 1:1
    private int cancelAmount;       // cancel_amount
    private String productId;       // product_id, FK, NN, N:1
    private String optionId;        // option_id, FK, NN N:1
    private String rfrDet;          // rfr_det, FK, NN, refund:common_code = N:1
    private String reason;          // reason, NN
    private String comment;         // comment, NN
    private Date date;              // date, NN
    private Date modDate;           // mod_date, NULL
    private String imgCode;         // img_code, NULL


}
