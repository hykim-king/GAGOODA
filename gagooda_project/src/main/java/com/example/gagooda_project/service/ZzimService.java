package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ZzimDto;

import java.util.List;

public interface ZzimService {
    int insert(ZzimDto zzim);
    int remove(int zzimId);
    ZzimDto selectOne(int userId,String productCode);
    List<ZzimDto> listByUserId(int userId);
}
