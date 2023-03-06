package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ZzimDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ZzimMapper {
    int insertOne(ZzimDto zzim);
    int deleteOne(int zzimId);
    ZzimDto findByProductCodeAndUserId(String productCode, int userId);
    int count(PagingDto paging, int userId);
    List<ZzimDto> pageAll(PagingDto paging, int userId);

    List<ZzimDto> findByUserId(int userId);
}
