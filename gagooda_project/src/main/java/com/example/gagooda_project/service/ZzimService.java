package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.dto.ZzimDto;

import java.util.List;
import java.util.Map;

public interface ZzimService {
    int insert(ZzimDto zzim);
    int remove(int zzimId);
    ZzimDto selectOne(String productCode, UserDto loginUser);
    List<ZzimDto> listByUserId(int userId);
    Map<String, ZzimDto> zzimCheck(List<ProductDto> productList, UserDto loginUser);


}
