package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.DeliveryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliveryMapperTest {

    @Autowired
    DeliveryMapper deliveryMapper;

    @Test
    void insertOne() {
        DeliveryDto delivery = new DeliveryDto();
        delivery.setInvoice(1357);
        delivery.setRequest("안전배송");
        delivery.setDDet("d0");
        delivery.setOrderId("1");
//        deliveryMapper.insertOne(delivery);
    }

    @Test
    void updateOne() {
        DeliveryDto delivery = new DeliveryDto();
//        delivery.setDeliveryId(1);
        delivery.setInvoice(1234);
        delivery.setRequest("빠른배송");
        delivery.setDDet("d0");
        delivery.setOrderId("1");
        deliveryMapper.updateOne(delivery);
//        DeliveryDto check = deliveryMapper.findById(1);
//        System.out.println(check);
    }

//    @Test
//    void findById() {
//        DeliveryDto delivery = deliveryMapper.findById(1);
//    }
}