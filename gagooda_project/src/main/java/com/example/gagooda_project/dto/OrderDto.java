package com.example.gagooda_project.dto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private int orderId;    // order_id 주문 아이디  PK  NN
    private Date date;      // date 주문일자   default(current_timestamp) NN
    private int userId;  // user_id 회원 아이디   NN
    private int addressId;   // address_id 배송지 아이디   NN
    /*private List<OrderDetailDto> orderDetailList; */  // 1:N 주문 상세 테이블 연결

}
