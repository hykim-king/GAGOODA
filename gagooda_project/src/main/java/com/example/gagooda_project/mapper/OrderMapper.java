package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ODetDto;
import com.example.gagooda_project.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    List<OrderDto> listOrder();
    List<OrderDto> findByPaging(); //PagingDto 추가해야함. Mapper에 query 추가 아직 안함.
    List<OrderDto> listByUserIdAndDate(int id, Date searchDate); //userId
    List<OrderDto> listOrderByStatus(String det);
    List<OrderDto> listOrderByDate(Date searchDate);
    OrderDto findById(String id); //orderId
    int insertOne(OrderDto dto);
    int updateStatus(String id, String det);//NULL이면 Integer
    int countByUserId(int id); //userId
    int countOrderByNotStatus(int id); //userId
    List<ODetDto> countByUserIdAndStatus(int id); //userId


}
