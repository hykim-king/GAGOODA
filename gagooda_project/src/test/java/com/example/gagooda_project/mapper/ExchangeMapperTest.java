package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ExchangeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ExchangeMapperTest {
    @Autowired
    ExchangeMapper exchangeMapper;
    @Test
    void pageByUserAndDate() {
//        List<ExchangeDto> exchangeList = exchangeMapper.pageByUserIdAndDate(1, 7);
//        System.out.println(exchangeList);
    }

    @Test
    void pageAll() {
        List<String> exDetList = new ArrayList<>();
        exDetList.add("ex0");
        exDetList.add("ex2");
//        List<ExchangeDto> exchangeList = exchangeMapper.pageAll(exDetList);
//        System.out.println(exchangeList);
    }

    @Test
    void insertOne() {
        ExchangeDto exchange = new ExchangeDto();
        exchange.setOrderDetailId(1);
        exchange.setOrderId("1111");
        exchange.setUserId(1);
        exchange.setUname("김111");
        exchange.setEmail("test@test.com");
        exchange.setPhone("01011111111");
        exchange.setAddressId(1);
        exchange.setCnt(11);
        exchange.setReason("그냥11");
        exchange.setPostCode("11111");
        exchange.setAddress("경기도11");
        exchange.setAddressDetail("상세주소11");
        exchange.setReceiverName("나나나11");
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
        exchange.setExDet("ex1");
        exchange.setReply("답글");
//        exchangeMapper.updateOne(exchange);
        ExchangeDto check = exchangeMapper.findById(1);
        System.out.println(check);
    }

    @Test
    void countByUserIdAndOrderDetailId() {
//        int cnt = exchangeMapper.countByUserIdAndOrderDetailId(1,1);
//        System.out.println(cnt);
    }
}