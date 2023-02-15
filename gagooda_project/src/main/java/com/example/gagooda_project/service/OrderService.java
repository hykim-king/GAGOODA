package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> orderList(PagingDto paging);


}
