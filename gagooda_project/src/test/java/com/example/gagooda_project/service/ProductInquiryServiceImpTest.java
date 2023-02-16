package com.example.gagooda_project.service;

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
        productInquiryService.showInquiries("HA10C15EE");
    }

    @Test
    void registerProductInquiry() {
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