package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductInquiryDto;

import java.util.List;

public interface ProductInquiryService {


//  productCode에 맞는 문의 리스트
    List<ProductInquiryDto> showInquiries(String ProductCode);

//  문의 등록
    int registerProductInquiry (ProductInquiryDto productInquiryDto);

//  문의 ID에 따른 문의리스트
    ProductInquiryDto showDetail (int productInquiryId);

//  전체 리스트(관리자)
    List<ProductInquiryDto> showProductInquiries(PagingDto paging);

//  문의 삭제
    int removeOne(int productInquiryId);

//  문의 답글 등록
    int modifyOne(ProductInquiryDto productInquiryDto);

//  공통코드
    List<CommonCodeDto> showCommonCode(String mstCode);

//  옵션코드
    List<OptionProductDto> showOptionProduct(String productCode);

//  상품별 문의 수
    int numPInquiryId (String productCode);

//  전체 문의 수
    int totalCount (PagingDto paging);

}
