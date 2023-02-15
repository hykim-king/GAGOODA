package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ExchangeDto;
import com.example.gagooda_project.mapper.ExchangeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeServiceImp implements ExchangeService {
    private ExchangeMapper exchangeMapper;

    public ExchangeServiceImp(ExchangeMapper exchangeMapper) {
        this.exchangeMapper = exchangeMapper;
    }

    @Override
    public List<ExchangeDto> pageAll(List<String> exDetList) {
        exchangeMapper.pageAll(exDetList);
        return null;
    }

    @Override
    public List<ExchangeDto> pageByUserAndDate(int userId, int period) {
        return null;
    }

    @Override
    public int insertOne(ExchangeDto exchange) {
        int insert = 0;
        insert += exchangeMapper.insertOne(exchange);
        return 0;
    }

    @Override
    public int updateOne(ExchangeDto exchange) {
        return 0;
    }

    @Override
    public int countByUserIdAndOrderDetailId(int userId, int orderDetailId) {
        return 0;
    }

    @Override
    public ExchangeDto findById(int id) {
        return null;
    }
}
