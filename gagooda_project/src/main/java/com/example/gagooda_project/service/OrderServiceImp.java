package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    private OrderMapper orderMapper;
    private OrderServiceImp(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }
    @Override
    public List<OrderDto> orderList(PagingDto paging, int userId) {
        int totalRows=orderMapper.count(paging,userId);
        paging.setTotalRows(totalRows);
        return orderMapper.pageAll(paging);
    }


}
