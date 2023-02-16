package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.DeliveryMapper;
import com.example.gagooda_project.mapper.OrderDetailMapper;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;
    private DeliveryMapper deliveryMapper;
    private OrderServiceImp(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper, DeliveryMapper deliveryMapper){
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.deliveryMapper = deliveryMapper;
    }
    @Override
    public List<OrderDto> orderList(PagingDto paging, int userId) {
        int totalRows=orderMapper.count(paging,userId);
        paging.setTotalRows(totalRows);
        return orderMapper.pageAll(paging);
    }

    @Override
    public OrderDto selectOne(String orderId) { return orderMapper.findById(orderId); }

    @Override
    public List<OrderDetailDto> orderDetailList(String orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    public int register(OrderDto order, DeliveryDto delivery) {
        int register = orderMapper.insertOne(order);
        System.out.println(register);
        for(OrderDetailDto orderDetail: order.getOrderDetailList()){
            register += orderDetailMapper.insertOne(orderDetail);
        }
        System.out.println(register);
        register += deliveryMapper.insertOne(delivery);
        System.out.println(register);
        return register;
    }

    @Override
    public DeliveryDto selectDelivery(String orderId) {
        return deliveryMapper.findByOrderId(orderId);
    }


}
