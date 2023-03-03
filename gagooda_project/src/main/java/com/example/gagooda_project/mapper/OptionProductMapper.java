package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionProductMapper {
    List<OptionProductDto> listByProductCode(String productCode);
    int insertOne(OptionProductDto optionProduct);
    int deleteByProductCode(String productCode);
    OptionProductDto findById(String id);
    int updateOne(OptionProductDto optionProduct);
    int deleteById(String productCode);
    int updateStock(int count,String optionCode);
}
