package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OptionProductDto;

public interface OptionProductService {
    public int removeWithProduct(String productCode);
    public OptionProductDto selectOne(String optionCode);
}
