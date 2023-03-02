package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ZzimDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ZzimMapper {
    int insertOne(ZzimDto zzim);
    int deleteOne(int zzimId);
    ZzimDto findByProductCodeAndUserId(String productCode, int userId);
}
