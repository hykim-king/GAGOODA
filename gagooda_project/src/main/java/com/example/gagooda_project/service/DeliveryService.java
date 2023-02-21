package com.example.gagooda_project.service;


import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.mapper.DeliveryMapper;


public interface DeliveryService {

    DeliveryDto selectOne(int deliveryId);
    int modifyOne(DeliveryDto delivery);
    DeliveryDto selectByOrderId(String orderId);


}
