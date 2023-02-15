package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ProductInquiryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInquiryMapper {

    List<ProductInquiryDto> listByProductCode(String productCode);

    int insertOne(ProductInquiryDto productInquiryDto);

    List<ProductInquiryDto> listAll();

    ProductInquiryDto findById(int id);

    int updateReplyInTable(ProductInquiryDto productInquiry);

    int deleteById(int id);

//    List<ProductInquiryDto> pageAll();

}
