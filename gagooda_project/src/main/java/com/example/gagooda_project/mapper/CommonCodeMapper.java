package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CommonCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    List<CommonCodeDto> listByMstCode(String mstCode);
    int insertOne(CommonCodeDto commonCode);
    int updateOne(CommonCodeDto commonCode);
//    int deleteOne(String mstCode);
//
}
