package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeDto {
    private int exchangeId;
    private int orderDetailId;
    private int userId;
    private String productId;
    private String exMst;
    private String exDet;
    private String rfrMst;
    private String rfrDet;
    private String exchaReason;
    private int exchaCount;
    private Date exchaDate;
    private int exchaState;
}
