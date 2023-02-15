package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import com.example.gagooda_project.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    private ProductMapper productMapper;

    public ProductServiceImp(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<ProductDto> showProducts() {
        return productMapper.listAll();
    }

    public ProductDto selectOne(String productCode) {
        return productMapper.findById(productCode);
    }
}
