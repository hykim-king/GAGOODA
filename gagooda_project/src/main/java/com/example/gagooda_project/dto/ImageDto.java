package com.example.gagooda_project.dto;

import lombok.Data;

@Data
public class ImageDto {
    private String imgCode;  // 이미지 코드  img_code  PK NN
    private int seq;         // 순서        seq      PK NN
    private String imgPath;  // 이미지 경로   img_path  NN

}
