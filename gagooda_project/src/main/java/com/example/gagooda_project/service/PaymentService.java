package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PaymentDto;
import com.example.gagooda_project.mapper.PaymentMapper;

public interface PaymentService {
    PaymentDto selectOne(String orderId);
    int register(PaymentDto payment);
    int delete(String orderId);
    int registerOrderPayment(PaymentDto payment, String orderId, String oDet);
}
