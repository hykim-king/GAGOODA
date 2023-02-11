package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CartDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int insertOne(CartDto dto);
    int deleteById(int id);  // cart_id
    int deleteAll(int id);   // user_id
    int countByUserId(int id);
    List<CartDto> listByUserId(int id);

}
