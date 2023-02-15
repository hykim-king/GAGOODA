package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ExchangeDto;

import java.util.List;

public interface ExchangeService {
    List<ExchangeDto> pageAll(List<String> exDetList);

    List<ExchangeDto> pageByUserAndDate(int userId, int period);

    int insertOne(ExchangeDto exchange);

    int updateOne(ExchangeDto exchange);

    int countByUserIdAndOrderDetailId(int userId, int orderDetailId);

    ExchangeDto findById(int id);
}
