package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    ProductDto findById(String productCode);
    int insertOne(ProductDto product);
    int updateOne(ProductDto product);
    List<ProductDto> listAll();
    int deleteById(String productCode);

    int count(PagingDto paging);
    int countForPaging(PagingDto paging, Map<String, Object> map);
    List<ProductDto> pageForPaging(PagingDto paging, Map<String, Object> map);
    List<ProductDto> pageAll(PagingDto paging);
//    List<ProductDto> pageAll();
    List<ProductDto> mainListBySales(String place);
}
