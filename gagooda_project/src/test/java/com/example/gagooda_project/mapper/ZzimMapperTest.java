package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ZzimDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ZzimMapperTest {

    @Autowired
    ZzimMapper zzimMapper;
    @Test
    void insertOne() {
        ZzimDto zzim = new ZzimDto();
        zzim.setUserId(1);
        zzim.setProductCode("HLC0E04EU");
        zzimMapper.insertOne(zzim);
    }

    @Test
    void deleteOne() {
        int delete = zzimMapper.deleteOne(1);
        System.out.println(delete);
    }

    @Test
    void findByProductCodeAndUserId() {
        ZzimDto zzim = zzimMapper.findByProductCodeAndUserId("HA00G01EEU",1);
        System.out.println(zzim);
    }

    @Test
    void findByUserId() {
        List<ZzimDto> zzimList = zzimMapper.findByUserId(1);
        System.out.println(zzimList);
    }
}