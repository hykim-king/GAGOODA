package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class ZzimDto {
    private int zzimId;
    private String productCode;
    private int userId;
    ProductDto product;

}
