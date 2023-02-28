package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PaymentDto;
import com.example.gagooda_project.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements PaymentService{
    PaymentMapper paymentMapper;

    public PaymentServiceImp(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDto selectOne(String orderId) {
        return paymentMapper.findByOrderId(orderId);
    }

    @Override
    public int register(PaymentDto payment) {
        return paymentMapper.insertOne(payment);
    }

    @Override
    public int delete(String orderId) {
        return paymentMapper.deleteByOrderId(orderId);
    }
}
