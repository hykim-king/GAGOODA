package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    PaymentDto findByOrderId(String orderId);
    int insertOne(PaymentDto payment);
    int deleteByOrderId(String orderId);
}
