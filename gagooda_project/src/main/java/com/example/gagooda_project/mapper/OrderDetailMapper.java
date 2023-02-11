package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    List<OrderDetailDto> findByOrderId(int id);
    OrderDetailDto findByOrderDetailId(int id);
    int countByOrderId(int id);
    int insertOne(OrderDetailDto dto);
}
