package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.dto.ZzimDto;
import com.example.gagooda_project.mapper.ProductMapper;
import com.example.gagooda_project.mapper.UserMapper;
import com.example.gagooda_project.mapper.ZzimMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ZzimServiceImp implements ZzimService{
    ZzimMapper zzimMapper;
    ProductMapper productMapper;
    UserMapper userMapper;

    public ZzimServiceImp(ZzimMapper zzimMapper, ProductMapper productMapper, UserMapper userMapper){
        this.zzimMapper = zzimMapper;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }
    @Override
    public int insert(ZzimDto zzim) {
        return zzimMapper.insertOne(zzim);
    }

    @Override
    public int remove(int zzimId) {
        return zzimMapper.deleteOne(zzimId);
    }

    @Override
    public ZzimDto selectOne(int userId, String productCode) {
        return zzimMapper.findByProductCodeAndUserId(productCode,userId);
    }

    @Override
    public List<ZzimDto> listByUserId(int userId) {
        return zzimMapper.findByUserId(userId);
    }

    @Override
    public Map<String, ZzimDto> zzimCheck(List<ProductDto> productList, UserDto loginUser) {
        Map<String,ZzimDto> zzimMap = new HashMap<String,ZzimDto>();
        for (ProductDto p : productList) {
            ZzimDto zzim = zzimMapper.findByProductCodeAndUserId(p.getProductCode(), loginUser.getUserId());
            zzimMap.put(p.getProductCode(),zzim);
        }
        return zzimMap;
    }
}
