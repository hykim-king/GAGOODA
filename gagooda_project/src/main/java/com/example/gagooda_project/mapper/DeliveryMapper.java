package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.DeliveryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {
    int insertOne(DeliveryDto dto);
    int updateOne(DeliveryDto dto);
    DeliveryDto findById(int id);
    DeliveryDto findByOrderId(String orderId);
    List<DeliveryDto> listAll(DeliveryDto dto);
    List<DeliveryDto> pageByUserIdAndDate(DeliveryDto dto);

}
