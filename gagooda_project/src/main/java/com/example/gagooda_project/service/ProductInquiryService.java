package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductInquiryDto;

import java.util.List;

public interface ProductInquiryService {

    List<ProductInquiryDto> showInquiries(String ProductCode);

    int registerProductInquiry (ProductInquiryDto productInquiryDto);

    ProductInquiryDto showDetail (int productInquiryId);

    List<ProductInquiryDto> showProductInquiries();

    int removeOne(int productInquiryId);

    int modifyOne(ProductInquiryDto productInquiryDto);

}
