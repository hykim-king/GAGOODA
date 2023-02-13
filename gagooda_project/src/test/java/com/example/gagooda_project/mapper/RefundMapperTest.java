package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.RefundDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefundMapperTest {
    @Autowired
    private RefundMapper refundMapper;

    @Test
    void pageByUserIdAndDate() {
        refundMapper.pageByUserIdAndDate(1,new Date(), 15);
    }

    @Test
    void insertOne() {
        RefundDto refund = new RefundDto();
        refund.setUserId(1);
        refund.setReceiverName("김김김");
        refund.setEmail("ddd@dddd.ddd");
        refund.setPhone("01011111111");
        refund.setOrderDetailId(1);
        refund.setOrderId("E11111");
        refund.setAddressId(1);
        refund.setCancelAmount(1111);
        refund.setReason("111");
        refund.setPostCode("11111");
        refund.setAddress("서울시");
        refund.setAddressDetail("1111빌딩");
        refund.setReceiverName("김111");
        refund.setReceiverPhone("11111111111");
        refund.setImgCode("imgcode111");
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
        List<RefundDto> refundList = refundMapper.pageAll();
        System.out.println(refundList);
    }

    @Test
    void updateOne() {
        RefundDto findRefund = refundMapper.findById(1);
        findRefund.setReply("코멘트 남깁니다~");
        findRefund.setModDate(new Date());
        findRefund.setRfDet("rf1");
        refundMapper.updateOne(findRefund);
        RefundDto resultRefund = refundMapper.findById(1);
        System.out.println(resultRefund);
    }
}