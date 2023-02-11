package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryDto findById();

    List<CategoryDto> listByParentId(int parentId);

    int insertOne(CategoryDto categoryDto);
}
