package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class PaymentDto {
    String orderId;     // order_id, pk, order_db : imp_uid(1:1)
    String impUid;
}
