package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.RefundDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefundMapperTest {
    @Autowired
    private RefundMapper refundMapper;
    @Autowired
    private CommonCodeMapper commonCodeMapper;

    @Test
    void pageByUserIdAndDate() {
        refundMapper.pageByUserIdAndDate(2,7);
    }

    @Test
    void insertOne() {
        RefundDto refund = new RefundDto();
        refund.setUserId(2);
        refund.setReceiverName("김김김");
        refund.setEmail("ddd@dddd.ddd");
        refund.setPhone("01022221111");
        refund.setOrderDetailId(1);
        refund.setOrderId("E22222");
        refund.setAddressId(1);
        refund.setCancelAmount(1111);
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
        List<String> rfDetList = new ArrayList<>();
        rfDetList.add("rf0");
        rfDetList.add("rf1");
        List<RefundDto> refundList = refundMapper.pageAll(rfDetList);
        System.out.println(refundList);
    }

    @Test
    void updateOne() {
        RefundDto findRefund = refundMapper.findById(1);
        findRefund.setReply("코멘트 남깁니다~");
        findRefund.setRfDet("rf1");
        refundMapper.updateOne(findRefund);
        RefundDto resultRefund = refundMapper.findById(1);
        System.out.println(resultRefund);
    }
}