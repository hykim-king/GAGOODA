package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.ProductInquiryDto;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.ProductInquiryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInquiryServiceImp implements ProductInquiryService{
    private ProductInquiryMapper productInquiryMapper;
    private CommonCodeMapper commonCodeMapper;

    public ProductInquiryServiceImp(ProductInquiryMapper productInquiryMapper,CommonCodeMapper commonCodeMapper) {
        this.productInquiryMapper = productInquiryMapper;
        this.commonCodeMapper = commonCodeMapper;
    }

    public List<ProductInquiryDto> showInquiries(String productCode){
        List<ProductInquiryDto> p = productInquiryMapper.listByProductCode(productCode);
        for (int i=0; i<p.size(); i++){
            String mstCode = p.get(i).getPiDet();
            String name ="";
            List<CommonCodeDto> comm = commonCodeMapper.listByMstCode("pi");
            for (int j=0; j<comm.size(); j++){
                if (comm.get(j).getDetCode() == mstCode) {
                    name = comm.get(j).getDetName();
                    System.out.println(name);
                }
            }
            p.get(i).setPiDet(name);
        }
        return p;
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

    @Override
    public int modifyOne(ProductInquiryDto productInquiryDto) {
        return productInquiryMapper.updateReplyInTable(productInquiryDto);
    }

}
