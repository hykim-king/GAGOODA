package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    void listAll() {
        orderMapper.listAll();
    }

    @Test
    void pageAll() {
    }

    @Test
    void listByUserIdAndDate() {
        orderMapper.listByUserIdAndDate(1, 7);
    }

    @Test
    void listByStatus() {
        orderMapper.listByStatus("o2");
    }

    @Test
    void listByDate() {
        orderMapper.listByDate(2);
    }

    @Test
    void findById() {
        orderMapper.findById("202302131235");
    }

    @Test
    void insertOne() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId("202302131235");
        orderDto.setUserId(2);
        orderDto.setUserEmail("username2@gmail.com");
        orderDto.setUserPhone("01012341234");
        orderDto.setAddress("어디구 여기구 저기로");
        orderDto.setAddressDetail("100동 1000호");
        orderDto.setAddressId(30);
        orderDto.setTotalPrice(1500000);
        orderDto.setUserName("username2");
        orderDto.setPostCode("12345");
        orderDto.setReceiverName("usertwo");
        orderDto.setReceiverPhone("01012341234");
        orderDto.setElevator(true);
        orderDto.setODet("o2");
        orderDto.setImgPath("thumbnail.png");
        orderMapper.insertOne(orderDto);

    }

    @Test
    void updateStatus() {
        orderMapper.updateStatus("202302131235","o4");
    }

    @Test
    void countByUserId() {
        orderMapper.countByUserId(2);
    }

    @Test
    void countByNotStatus() {
        orderMapper.countByNotStatus(2,"o2");
    }

    @Test
    void countByUserIdAndStatus() {
        orderMapper.countByUserIdAndStatus(2,"od2");

    }
}