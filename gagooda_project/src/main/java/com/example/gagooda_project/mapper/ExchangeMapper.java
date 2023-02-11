package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ExchangeDto;
import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExchangeMapper {
    List<ExchangeDto> pageByUserAndDate(int id, Date curentDate);
    List<ExchangeDto> pageAll();
    int insertOne(RefundDto refund);
    RefundDto findById(int id);
    int updateOne(RefundDto refund);



}
