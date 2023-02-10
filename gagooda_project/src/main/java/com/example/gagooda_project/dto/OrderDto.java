package com.example.gagooda_project.dto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private int orderId;// order_id PK NN 주문 아이디 AUTO_INCREMENT
    private int userId;// user_id (USER:ORDER)1:N NN 사용자 ID
    private int addressId;// address_id 배송지 아이디
    private Date date;      //주문일자

    private int totalPrice; //
    private String imgPath; //
    private String oDet; //
    private List<OrderDetailDto> orderDetailList;   // 1:N 주문 상세 테이블 연결
}
