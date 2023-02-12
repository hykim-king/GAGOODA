package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ExchangeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExchangeMapperTest {
    @Autowired
    ExchangeMapper exchangeMapper;
    @Test
    void pageByUserAndDate() {
        List<ExchangeDto> exchangeList = exchangeMapper.pageByUserAndDate(1, 30);
        System.out.println(exchangeList);
    }

    @Test
    void pageAll() {
        List<ExchangeDto> exchangeList = exchangeMapper.pageAll();
        System.out.println(exchangeList);
    }

    @Test
    void insertOne() {
        ExchangeDto exchange = new ExchangeDto();
        exchange.setOrderDetailId(2);
        exchange.setOrderId("2222");
        exchange.setUserId(1);
        exchange.setAddressId(1);
        exchange.setCount(1);
        exchange.setReason("그냥22");
        exchange.setPostCode("22222");
        exchange.setAddress("경기도22");
        exchange.setAddressDetail("상세주소22");
        exchange.setReceiverName("나나나22");
        exchange.setReceiverPhone("11111111111");
        exchange.setRfrDet("rfr2");
        exchangeMapper.insertOne(exchange);
    }

    @Test
    void findById() {
        ExchangeDto exchange = exchangeMapper.findById(1);
        System.out.println(exchange);
    }

    @Test
    void updateOne() {
        ExchangeDto exchange = new ExchangeDto();
        exchange.setExchangeId(1);
        exchange.setExDet("ex2");
        exchange.setComment("답글");
        exchange.setModDate(new Date());
        exchangeMapper.updateOne(exchange);
        ExchangeDto check = exchangeMapper.findById(1);
        System.out.println(check);
    }

    @Test
    void countByUserIdAndOrderDetailId() {
        exchangeMapper.countByUserIdAndOrderDetailId(1,1);
    }
}