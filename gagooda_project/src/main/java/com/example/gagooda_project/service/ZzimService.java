package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ZzimDto;

public interface ZzimService {
    int insert(ZzimDto zzim);
    int remove(int zzimId);
    ZzimDto selectOne(int userId,String productCode);
}
