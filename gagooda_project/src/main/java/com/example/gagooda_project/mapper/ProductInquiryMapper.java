package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.ProductInquiryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInquiryMapper {

    List<ProductInquiryDto> listByProductCode(String productCode, PagingDto paging);

    int insertOne(ProductInquiryDto productInquiryDto);

    List<ProductInquiryDto> listAll();

    ProductInquiryDto findById(int id);

    int updateReplyInTable(ProductInquiryDto productInquiry);

    int deleteById(int id);

    int countByPInquiryId(String productCode, PagingDto paging);

    List<ProductInquiryDto> pageAll(PagingDto paging);

    int count(PagingDto paging);

    List<ProductInquiryDto> listByProductCodeAll(String productCode);
}
