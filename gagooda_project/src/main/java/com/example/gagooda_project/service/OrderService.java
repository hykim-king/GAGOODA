package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> orderList(PagingDto paging, int userId);
    OrderDto selectOne(String orderId);
    int register(OrderDto order, DeliveryDto delivery);

}
