package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ExchangeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExchangeMapper {
    List<ExchangeDto> pageByUserAndDate(int userId, int period);
    List<ExchangeDto> pageAll();
    int insertOne(ExchangeDto exchange);
    ExchangeDto findById(int id);
    int updateOne(ExchangeDto exchange);
    int countByUserIdAndOrderDetailId(int userId, int orderDetailId);
}
