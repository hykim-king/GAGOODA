package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.PagingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int insertOne(CartDto cart);
    CartDto findById(int id);
    int deleteById(int id);  // cart_id
    int deleteByUserId(int userId);   // user_id
    int countByUserId(int userId);
    List<CartDto> listByUserId(int userId);
    int updateOne(CartDto cart);
    int count(PagingDto paging, int userId);
//    int updateOne(int cnt, int userId, String optionCode);
    CartDto findByUserIdAndOptionCode(int userId, String optionCode);

    List<CartDto> pageAll(PagingDto paging, int userId);
}
