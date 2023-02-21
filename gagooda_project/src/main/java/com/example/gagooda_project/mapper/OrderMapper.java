package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ODetDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.RefundDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    List<OrderDto> listAll();

    List<OrderDto> pageAll(PagingDto paging, int userId, int dates); //PagingDto 추가해야함. Mapper에 query 추가 아직 안함.
    int count(PagingDto paging,int userId,int dates);

    List<OrderDto> listByUserIdAndDate(int userId, int dates); //userId

    // Date searchDate -> int dates
    // 현재로부터 몇일 전을 찾기 위한 함수 아니었나요..?
    // int id가 user_id 일 경우 모두 userId로 바꿨습니다
    List<OrderDto> listByStatus(String oDet);

    // 상태들은 모두 oDet로 바꿨습니다.
    List<OrderDto> listByDate(int dates);

    // Date searchDate -> int dates
    // 현재로부터 몇일 전을 찾기 위한 함수 아니었나요..?
    OrderDto findById(String id); //orderId

    int insertOne(OrderDto order);

    int updateStatus(String id, String oDet);//NULL이면 Integer

    int countByUserId(int userId); //userId

    int countByNotStatus(int userId, String oDet); //userId

    List<ODetDto> countByUserIdAndStatus(int userId); //userId
    //ODetDto로 변환시켜줄 resultMap 작성했습니다.
    List<OrderDto> pageAdminAll(Map<String, Object> searchFilter);
    int countPageAll(Map<String, Object> searchFilter);


}