package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductInquiryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductInquiryMapperTest {
    @Autowired
    ProductInquiryMapper productInquiryMapper;

    @Test
    void listByProductCode() {
        PagingDto paging = new PagingDto();
        paging.setOrderField("reply_date");
        productInquiryMapper.listByProductCode("PDT001", paging);
    }

    @Test
    void insertOne() {
        ProductInquiryDto pid = new ProductInquiryDto();
        pid.setUserId(1);
        pid.setOptionCode("PDT001_001");
        pid.setProductCode("PDT001");
        pid.setTitle("test");
        pid.setContent("test입니다");
        pid.setSecret(true);
        pid.setRegDate(new Date());
        pid.setPiDet("pi0");
        productInquiryMapper.insertOne(pid);
    }

    @Test
    void listAll() {
        productInquiryMapper.listAll();
    }

    @Test
    void findById() {
        productInquiryMapper.findById(1);
    }

    @Test
    void updateReplyInTable() {
        ProductInquiryDto pInquiry = productInquiryMapper.findById(1);
        pInquiry.setReply("이것은 답변입니다");
        pInquiry.setReplyId(9);
        productInquiryMapper.updateReplyInTable(pInquiry);
    }

    @Test
    void deleteById() {
        productInquiryMapper.deleteById(1);
    }

    @Test
    void pageAll() {
    }

    @Test
    void countByPInquiryId() {
        PagingDto paging = new PagingDto();
        paging.setOrderField("reply_date");
        productInquiryMapper.countByPInquiryId("HA10C15EE", paging);
    }

    @Test
    void testPageAll() {
    }

    @Test
    void countByUserId() {
        System.out.println(productInquiryMapper.countByUserId(1));
    }

    @Test
    void listByUserId() {
        PagingDto paging = new PagingDto();
        paging.setOrderField("p_inquiry_id");
        paging.setPage(1);
        paging.setDirect("DESC");
        paging.setRows(5);
        paging.setStartRow(1);
        paging.setTotalRows(productInquiryMapper.countByUserId(1));
        productInquiryMapper.listByUserId(1);
    }
}