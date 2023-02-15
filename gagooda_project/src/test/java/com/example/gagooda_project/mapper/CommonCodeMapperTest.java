package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CommonCodeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommonCodeMapperTest {
    @Autowired
    CommonCodeMapper commonCodeMapper;
    @Test
    void listByMstCode() {
        List<CommonCodeDto> commonCodeList = commonCodeMapper.listByMstCode("rf");
    }

    @Test
    void insertOne() {
        CommonCodeDto commonCode = new CommonCodeDto();
        commonCode.setMstCode("rf");
        commonCode.setDetCode("rf3");
        commonCode.setMstName("환불처리상태");
        commonCode.setDetName("반품 요청 확인");
        commonCodeMapper.insertOne(commonCode);
    }

    @Test
    void modifyOne() {
        CommonCodeDto commonCode = new CommonCodeDto();
        commonCode.setMstCode("t");
        commonCode.setDetCode("t01");
        commonCode.setModDt(new Date());
        commonCode.setUserYn(true);
        commonCodeMapper.updateOne(commonCode);
        System.out.println(commonCodeMapper.listByMstCode("rf"));
    }
}