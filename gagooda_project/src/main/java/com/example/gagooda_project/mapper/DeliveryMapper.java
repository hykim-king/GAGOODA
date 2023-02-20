package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.DeliveryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeliveryMapper {
    int insertOne(DeliveryDto dto);
    int updateOne(DeliveryDto dto);
    DeliveryDto findById(int id);
    DeliveryDto findByOrderId(String orderId);
    List<DeliveryDto> listAll(Map<String, String> searchFilter);
    List<DeliveryDto> pageByUserIdAndDate(int userId, int period, String startDate, String endDate, String detCode);

}
