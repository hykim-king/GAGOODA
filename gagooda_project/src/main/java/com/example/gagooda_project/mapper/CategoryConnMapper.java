package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CategoryConnDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryConnMapper {
    List<CategoryConnDto> listByCategoryId(int categoryId);
    List<CategoryConnDto> listByProductCode(String productCode);
    int insertOne(CategoryConnDto categoryConn);
    int deleteByProductCode(String productCode);
}
