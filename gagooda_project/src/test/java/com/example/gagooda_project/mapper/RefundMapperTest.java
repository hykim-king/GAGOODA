package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.RefundDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefundMapperTest {
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private CommonCodeMapper commonCodeMapper;

    @Test
    void pageByUserIdAndDate() {
        PagingDto pagingDto = new PagingDto(1,10);
        refundMapper.pageByUserIdAndDate(1,15, "2023-02-01", "2023-02-16", "rf1", pagingDto);
    }

    @Test
    void insertOne() {
        RefundDto refund = new RefundDto();
        refund.setUserId(1);
        refund.setReceiverName("김김김");
        refund.setEmail("ddd@dddd.ddd");
        refund.setPhone("01022221111");
        refund.setOrderDetailId(1);
        refund.setOrderId("00010");
        refund.setAddressId(1);
        refund.setCancelAmount(10000);
        refund.setReason("222");
        refund.setPostCode("2222");
        refund.setAddress("서울시");
        refund.setAddressDetail("22221빌딩");
        refund.setUname("김2222");
        refund.setReceiverPhone("22222222222");
        refund.setImgCode("imgcode222");
        refund.setRfrDet("rfr1");
        refundMapper.insertOne(refund);

    }

    @Test
    void findById() {
        RefundDto findRefund = refundMapper.findById(1);
        System.out.println(findRefund);
    }

    @Test
    void pageAll() {
        Map<String,Object> rfDetList = null;
        List<RefundDto> refundList = refundMapper.pageAll(rfDetList);
        System.out.println(refundList);
    }

    @Test
    void updateOne() {
        RefundDto findRefund = refundMapper.findById(33);
        refundMapper.updateOne(findRefund, "admin");
        RefundDto resultRefund = refundMapper.findById(33);
        System.out.println(resultRefund);
    }

}