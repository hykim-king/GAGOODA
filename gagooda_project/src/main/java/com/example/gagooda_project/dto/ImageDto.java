package com.example.gagooda_project.dto;

import lombok.Data;

@Data

public class Comm_imgDto {
//    이미지 코드 사용되는 게시테이블의 이미지 코드(참조하는 테이블 연결 코드)
//    , 테이블 타입, 이미지 경로,
    private String product_img;         // 상품 이미지
    private String review_img;          // 상품 후기 이미지
    private String refund_img;          // 환불 이미지
    private String event_img;           // 이벤트 이미지
    private String inquiry_img;         // 문의 이미지
}
