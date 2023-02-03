package com.example.gagooda_project.dto;

import lombok.Data;

@Data

public class ImageDto {
//    상품 product - img_code
//    상품후기 review - img_code
//    환불 refund - img_code
//    이벤트 event - img_code
//    문의 inquiry - img_code

//    커뮤니티 이미지 comm_ing - img_path

    private String img_path;        // 이미지 경로 PK NN (COMMENT '이미지 경로', -- 이미지 경로) 오름차순(ASC)
    private String img_code;        // 이미지 코드 PK NN (COMMENT '이미지 코드' -- 이미지 코드)
}
