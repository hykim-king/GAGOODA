package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CartDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int insertOne(CartDto dto);
    int deleteById(int id);  // cart_id
    int deleteByUserId(int userId);   // user_id
    int countByUserId(int userId);
    List<CartDto> listByUserId(int userId);

}
