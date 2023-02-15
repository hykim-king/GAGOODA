package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderServiceImp(OrderMapper orderMapper){this.orderMapper = orderMapper;}
    @Override
    public List<OrderDto> orderList(PagingDto paging) {
        return null;
    }
}
