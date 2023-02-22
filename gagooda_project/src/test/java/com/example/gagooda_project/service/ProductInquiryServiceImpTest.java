package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductInquiryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductInquiryServiceImpTest {
    @Autowired
    private ProductInquiryService productInquiryService;

    @Test
    void showInquiries() {
        PagingDto paging = new PagingDto();
        paging.setOrderField("reply_date");
        System.out.println(productInquiryService.showInquiries("HA10C15EE", paging));
    }

    @Test
    void registerProductInquiry() {
        ProductInquiryDto p = new ProductInquiryDto();
        p.setPiDet("pi0");
        p.setSecret(true);
        p.setRegDate(new Date());
        p.setUserId(1);
        p.setProductCode("HA310611");
        p.setOptionCode("HA310611_DIO");
        productInquiryService.registerProductInquiry(p);
        System.out.println(p);
    }

    @Test
    void showDetail() {
    }

    @Test
    void modifyOne() {
        ProductInquiryDto p = productInquiryService.showDetail(2);
        p.setReply("test");
        p.setRegDate(new Date());
        p.setReplyId(1);
        productInquiryService.modifyOne(p);
    }
}