package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDto findById(String productCode);
    int insertOne(ProductDto product);
    int updateOne(ProductDto product);
    List<ProductDto> listAll();
    int deleteById(String productCode);
//    List<ProductDto> pageAll();
}
