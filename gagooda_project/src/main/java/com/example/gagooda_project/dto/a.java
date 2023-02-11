package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
class OptionProductDto {
    private String optionId; // 옵션상품 ID option_id PK NN
    private String productId; // 상품 ID product_id FK NN (상품:옵션상품 = 1:N)
    private String name; // 옵션 상품 이름 NN
    private int price; // 상품 가격 NN
    private int stock; // 총 수량 NN
    private List<OptionProductDto> refundList;
}
@Data
class CommonCodeDto {
    private String mstCode; // 마스터 코드 PK NN
    private String detCode; // 상세코드 PK NN
    private String mstName; // 마스터코드명 NN
    private String detName; // 상세코드명 NN
    private int seq; // 순서 NULL
    private Boolean userYn; // 사용여부 NULL
    private Date regDt; // 등록일 NN
    private String regId; // 등록자 NN
    private Date modDt; // 수정일 NN
    private String modId; // 수정자 NN
    private List<CommonCodeDto> refundList;
}