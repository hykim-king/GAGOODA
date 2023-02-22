package com.example.gagooda_project.service;


import com.example.gagooda_project.dto.DeliveryDto;

import java.util.List;
import java.util.Map;


public interface DeliveryService {

    public int insertOne(DeliveryDto delivery);
    public int modifyOne(DeliveryDto delivery);
    DeliveryDto selectByOrderId(String orderId);
    public List<DeliveryDto> showDeliveryList(Map<String,Object> searchFilter);
    public int removeOne(String orderId);

}
