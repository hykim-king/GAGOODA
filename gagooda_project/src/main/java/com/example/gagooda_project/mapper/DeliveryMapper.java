package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.DeliveryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeliveryMapper {

    int updateOne(DeliveryDto dto);
    DeliveryDto findByOrderId(String orderId);
    List<DeliveryDto> listAll(Map<String, Object> searchFilter); // 관리자 입장 다 뽑아보기
    int countListAll(Map<String, Object> searchFilter);
    int deleteOne(String orderId);
    int insertOne(DeliveryDto delivery);
}
