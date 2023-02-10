package com.example.gagooda_project.dto;
import lombok.Data;

@Data
public class OrderDetailDto {
    private int orderDetailId;  // order_detail_id 주문 상세 아이디  PK NN
    private String optionName;  // option_name 옵션 상품명 NN
    private int count;          // count 상품 수량 NN
    private int price;          // total_price 상품 총 값    NN
    private int orderId;        // order_id 주문 아이디   NN
    private String productId;   // product_id 상품 아이디   NN
    private String odMst;       // 주문 상태_mst (공통코드)   NN
    private String odDet;       // 주문 상태_det (공통코드)   NN
}
