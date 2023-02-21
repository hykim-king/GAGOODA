package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.DeliveryMapper;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeliveryServiceImp implements DeliveryService{
    private DeliveryMapper deliveryMapper;
    private OrderMapper orderMapper;
    private CommonCodeMapper commonCodeMapper;

    public DeliveryServiceImp(DeliveryMapper deliveryMapper, OrderMapper orderMapper, CommonCodeMapper commonCodeMapper) {
        this.deliveryMapper = deliveryMapper;
        this.orderMapper = orderMapper;
        this.commonCodeMapper = commonCodeMapper;
    }

    @Override
    public int insertOne(DeliveryDto delivery) {
        return deliveryMapper.insertOne(delivery);
    }

    @Override
    public int modifyOne(DeliveryDto delivery) {
        return deliveryMapper.updateOne(delivery);
    }

    @Override
    public DeliveryDto selectByOrderId(String orderId) {
        return deliveryMapper.findByOrderId(orderId);
    }

    @Override
    public List<DeliveryDto> showDeliveryList(Map<String, Object> searchFilter) {
        return null;
    }

    @Override
    public int removeOne(String orderId) {
        return deliveryMapper.deleteOne(orderId);
    }

}
