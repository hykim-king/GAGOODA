package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PaymentMapperTest {
    @Autowired
    private PaymentMapper paymentMapper;




    @Test
    void findByOrderId() {
        System.out.println(paymentMapper.findByOrderId("00010"));
    }

    @Test
    void insertOne() {
        PaymentDto payment = new PaymentDto();
        payment.setOrderId("00010");
        payment.setImpUid("test342fd");
        paymentMapper.insertOne(payment);
    }
}