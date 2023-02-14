package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductInquiryDto;
import com.example.gagooda_project.mapper.ProductInquiryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInquiryServiceImp implements ProductInquiryService{
    private ProductInquiryMapper productInquiryMapper;

    public ProductInquiryServiceImp(ProductInquiryMapper productInquiryMapper) {
        this.productInquiryMapper = productInquiryMapper;
    }

    public List<ProductInquiryDto> showInquiries(String productCode){
        return productInquiryMapper.listByProductCode(productCode);
    }

    @Override
    public int registerProductInquiry(ProductInquiryDto productInquiryDto) {
        return productInquiryMapper.insertOne(productInquiryDto);
    }

    @Override
    public ProductInquiryDto showDetail(int productInquiryId) {
        return productInquiryMapper.findById(productInquiryId);
    }

    @Override
    public List<ProductInquiryDto> showProductInquiries() {
        return productInquiryMapper.listAll();
    }

    @Override
    public int removeOne(int productInquiryId) {
        return productInquiryMapper.deleteById(productInquiryId);
    }

}
