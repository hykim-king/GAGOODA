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
    public List<ExchangeDto> list(List<String> exDetList) {
        return exchangeMapper.pageAll(exDetList);
    }

    @Override
    public List<ExchangeDto> orderInDate(int userId, int period) {
        return exchangeMapper.pageByUserAndDate(userId, period);
    }

    @Override
    public int register(ExchangeDto exchange) {
        return exchangeMapper.insertOne(exchange);
    }

    @Override
    public int modify(ExchangeDto exchange) {
        return exchangeMapper.updateOne(exchange);
    }

    @Override
    public int countByUserIdAndOrderDetailId(int userId, int orderDetailId) {
        return exchangeMapper.countByUserIdAndOrderDetailId(userId, orderDetailId);
    }

    @Override
    public ExchangeDto findById(int id) {
        return exchangeMapper.findById(id);
    }
}
