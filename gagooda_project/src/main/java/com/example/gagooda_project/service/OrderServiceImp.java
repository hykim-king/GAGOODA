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
    private OrderServiceImp(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper){
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }
    @Override
    public List<OrderDto> orderList(PagingDto paging, int userId) {
        int totalRows=orderMapper.count(paging,userId);
        paging.setTotalRows(totalRows);
        return orderMapper.pageAll(paging);
    }

    @Override
    public int register(OrderDto order, DeliveryDto delivery) {
        int register =0;
        for(OrderDetailDto orderDetail: order.getOrderDetailList()){
            register += orderDetailMapper.insertOne(orderDetail);
        }
        int deliveryRegister = deliveryMapper.insertOne(delivery);
        return orderMapper.insertOne(order);
    }


}
