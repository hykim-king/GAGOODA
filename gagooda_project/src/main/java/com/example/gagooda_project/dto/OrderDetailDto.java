package com.example.gagooda_project.dto;
import lombok.Data;

@Data
public class OrderDetailDto {
    private int orderDetailId;  // 주문 상세 아이디
    private String optionName;  // 옵션 상품명
    private int count;          // 상품 수량
    private int price;          // 상품 총 값
    private int orderId;        // 주문 아이디
    private String productId;   // 상품 아이디
    private String odMst;       // 주문 상태_mst (공통코드)
    private String odDet;       // 주문 상태_det (공통코드)
}
