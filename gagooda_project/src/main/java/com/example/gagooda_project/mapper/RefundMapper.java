package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface RefundMapper {
    List<RefundDto> pageByUserIdAndDate(int userId, int period);

    int insertOne(RefundDto refund);

    RefundDto findById(int id);

    List<RefundDto> pageAll(List<String> rfCodeList);

    int updateOne(RefundDto refund);

}
