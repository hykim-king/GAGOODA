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
        List<CommonCodeDto> commonCodeList = commonCodeMapper.listByMstCode("t");
    }

    @Test
    void insertOne() {
        CommonCodeDto commonCode = new CommonCodeDto();
        commonCode.setMstCode("t");
        commonCode.setDetCode("t01");
        commonCode.setMstName("테스트");
        commonCode.setDetName("테스트01");
        commonCode.setRegId("김김김");
        commonCode.setModId("김김김");
        commonCodeMapper.insertOne(commonCode);
    }

    @Test
    void modifyOne() {
        CommonCodeDto commonCode = new CommonCodeDto();
        commonCode.setMstCode("t");
        commonCode.setDetCode("t01");
        commonCode.setModId("테슷흐");
        commonCode.setModDt(new Date());
        commonCode.setUserYn(true);
        commonCodeMapper.modifyOne(commonCode);
        System.out.println(commonCodeMapper.listByMstCode("t"));
    }
}