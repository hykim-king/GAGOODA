package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface RefundMapper {
    List<RefundDto> pageByUserIdAndDate(int userId, int period, String startDate, String endDate, String detCode, PagingDto paging);

    int countByUserIdAndDate(int userId, int period, String startDate, String endDate, String detCode);

    int insertOne(RefundDto refund);

    RefundDto findById(int id);

    List<RefundDto> pageAll(Map<String, Object> searchFilter);

    int countPageAll(Map<String, Object> searchFilter);

    int updateOne(RefundDto refund, String auth);

    List<RefundDto> findByOrderDetailId(int orderDetailId);

    int countByOrderId(String orderId);

    int countAll();
    int countByUserId(int userId);

}
