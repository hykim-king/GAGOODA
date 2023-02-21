package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductInquiryDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.OptionProductMapper;
import com.example.gagooda_project.mapper.ProductInquiryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInquiryServiceImp implements ProductInquiryService{
    private ProductInquiryMapper productInquiryMapper;
    private CommonCodeMapper commonCodeMapper;
    private OptionProductMapper optionProductMapper;

    public ProductInquiryServiceImp(ProductInquiryMapper productInquiryMapper,
                                    CommonCodeMapper commonCodeMapper,
                                    OptionProductMapper optionProductMapper) {
        this.productInquiryMapper = productInquiryMapper;
        this.commonCodeMapper = commonCodeMapper;
        this.optionProductMapper = optionProductMapper;
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
    public List<ProductInquiryDto> showProductInquiries(PagingDto paging) {
        int totalRows = productInquiryMapper.count(paging);
        paging.setTotalRows(totalRows);
        return productInquiryMapper.pageAll(paging);
    }

    @Override
    public int removeOne(int productInquiryId) {
        return productInquiryMapper.deleteById(productInquiryId);
    }

    @Override
    public int modifyOne(ProductInquiryDto productInquiryDto) {
        return productInquiryMapper.updateReplyInTable(productInquiryDto);
    }

    @Override
    public List<CommonCodeDto> showCommonCode(String mstCode) {
        return commonCodeMapper.listByMstCode(mstCode);
    }

    @Override
    public List<OptionProductDto> showOptionProduct(String productCode) {
        return optionProductMapper.listByProductCode(productCode);
    }

    @Override
    public int numPInquiryId(String productCode) {
        return productInquiryMapper.countByPInquiryId(productCode);
    }

    @Override
    public int totalCount(PagingDto paging) {
        return productInquiryMapper.count(paging);
    }

}
