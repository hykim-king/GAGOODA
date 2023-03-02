package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PaymentDto;
import com.example.gagooda_project.mapper.OrderMapper;
import com.example.gagooda_project.mapper.PaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImp implements PaymentService{
    PaymentMapper paymentMapper;
    OrderMapper orderMapper;

    public PaymentServiceImp(PaymentMapper paymentMapper,
                             OrderMapper orderMapper) {
        this.paymentMapper = paymentMapper;
        this.orderMapper = orderMapper;
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

    @Transactional
    public int registerOrderPayment(PaymentDto payment, String orderId, String oDet) {
        int register = 0;
        if(payment != null){
            register += paymentMapper.insertOne(payment);
            System.out.println("payment register 후에 "+register);
        }
        if(orderId != null && oDet != null){
            register += orderMapper.updateStatus(orderId,oDet);
        }
        return register;
    }
}
