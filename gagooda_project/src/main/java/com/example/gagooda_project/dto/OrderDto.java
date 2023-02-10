package com.example.gagooda_project.dto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private String oDet; //
    private int orderId;    // 주문 아이디
    private Date date;      // 주문일자
    private int userId;  // 회원 아이디
    private int addressId;   // 배송지 아이디
    private int totalPrice; //
    private String imgPath; //
    private List<OrderDetailDto> orderDetailList;   // 1:N 주문 상세 테이블 연결
}
