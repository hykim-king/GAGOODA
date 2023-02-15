package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OrderDetailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailMapperTest {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Test
    void findByOrderId() {
        orderDetailMapper.findByOrderId("00010");
    }

    @Test
    void findById() {
        orderDetailMapper.findById(10);
    }

    @Test
    void countByOrderId() {
        orderDetailMapper.countByOrderId("00020");

    }

    @Test
    void insertOne() {
        OrderDetailDto orderDetail = new OrderDetailDto();
        orderDetail.setOrderId("00010");
        orderDetail.setCnt(3);
        orderDetail.setProductCode("PDT003");
        orderDetail.setOptionCode("PDT003_003");
        orderDetail.setPrice(1000000);
        orderDetail.setTotalPrice(1012030123);
        orderDetail.setOptionName("chair");
        orderDetailMapper.insertOne(orderDetail);
    }
}