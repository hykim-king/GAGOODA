package com.example.gagooda_project.dto;
import lombok.Data;

@Data
public class OrderDetailDto {
    private int orderDetailId;      // order_detail_id PK NN 주문 상세 아이디
    private int orderID;            // order_id PK & FK (ORDER : ORDER_DETAIL) 1:N NN 주문 아이디
    private String productId;       // product_id FK (ORDER_DETAIL:PRODUCT) 1:1 NN 상품 아이디
    private String optionId;        // option_id FK (ORDER_DETAIL:OPTION_PRODUCT) 1:1 NN 상품 아이디
    private int count;              // count NN 상품 수량
    private int price;              //price NN 상품 별 가격
    private int totalPrice;         // total_price NN 상품별 총 값

}