package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    List<OrderDetailDto> findByOrderId(String orderId);
    // id -> orderId
    OrderDetailDto findById(int id);
    // findByOrderDetailId -> findById
    int countByOrderId(String orderId);
    // id -> orderId
    int insertOne(OrderDetailDto orderDetail);
    // dto -> orderDetail
    int deleteByOrderId(String orderId);
}
