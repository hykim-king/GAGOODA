package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.mapper.OptionProductMapper;
import org.springframework.stereotype.Service;

@Service
public class OptionProductServiceImp implements OptionProductService{
    OptionProductMapper optionProductMapper;

    public OptionProductServiceImp(OptionProductMapper optionProductMapper) {
        this.optionProductMapper = optionProductMapper;
    }

    public int removeWithProduct(String productCode) {
        return optionProductMapper.deleteByProductCode(productCode);
    }

    public OptionProductDto selectOne(String optionCode) {
        return optionProductMapper.findById(optionCode);
    }
    public int modifyOne(OptionProductDto optionProduct) {
        return optionProductMapper.updateOne(optionProduct);
    }
}
