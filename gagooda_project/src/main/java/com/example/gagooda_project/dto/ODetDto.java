package com.example.gagooda_project.dto;
import lombok.Data;

@Data
public class ODetDto { //OrderMappr의 countByUserIdAndStatus를 위한 Dto
    private String oDet;
    private int count;
}
