package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ExchangeDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ExchangeMapper {
//    List<ExchangeDto> pageByUserAndDate(int userId, int period);
//    List<ExchangeDto> pageAll(List<String> exDetList);
//    int insertOne(ExchangeDto exchange);
//    ExchangeDto findById(int id);
//    int updateOne(ExchangeDto exchange);
//    int countByUserIdAndOrderDetailId(int userId, int orderDetailId);
    List<ExchangeDto> pageByUserIdAndDate(int userId, int period, String startDate, String endDate, String detCode, PagingDto paging);

    int countByUserIdAndDate(int userId, int period, String startDate, String endDate, String detCode);

    int insertOne(ExchangeDto exchange);

    ExchangeDto findById(int id);

    List<ExchangeDto> pageAll(Map<String, Object> searchFilter);

    int countPageAll(Map<String, Object> searchFilter);

    int updateOne(ExchangeDto exchange, String auth);

    List<ExchangeDto> findByOrderDetailId(int orderDetailId);

    int countByOrderId(String orderId);

    int countAll();
    int countByUserId(int userId);
}
