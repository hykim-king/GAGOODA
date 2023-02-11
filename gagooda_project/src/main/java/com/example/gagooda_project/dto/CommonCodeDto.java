package com.example.gagooda_project.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommonCodeDto {
    private String mstCode; // mst_code | 마스터 코드 | NN
    private String detCode; // det_code | 상세코드 | NN
    private String mstName; // mst_name | 마스터코드명 | NN
    private String detName; // det_name | 상세코드명 | NN
    private int seq;        // 순서
    private boolean userYn; // user_yn | 사용여부
    private Date regDt;     // reg_dt | 등록일 | NN | current timestamp
    private String regId;   // reg_id | 등록자 | NN
    private Date modDt;     // mod_dt | 수정일 | NN | current timestamp
    private String modId;   // mod_id | 수정자 | NN
}
