package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import com.example.gagooda_project.mapper.OptionProductMapper;
import com.example.gagooda_project.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService{
    private ProductMapper productMapper;
    private CategoryConnMapper categoryConnMapper;
    private OptionProductMapper optionProductMapper;

    public ProductServiceImp(ProductMapper productMapper,
                             CategoryConnMapper categoryConnMapper,
                             OptionProductMapper optionProductMapper) {
        this.productMapper = productMapper;
        this.categoryConnMapper = categoryConnMapper;
        this.optionProductMapper = optionProductMapper;
    }

    public List<ProductDto> showProducts() {
        return productMapper.listAll();
    }

    public ProductDto selectOne(String productCode) {
        return productMapper.findById(productCode);
    }

    public int insert(ProductDto product) {
        int insert = productMapper.insertOne(product);
        for (CategoryConnDto categoryConn : product.getCategoryConnList()) {
            insert += categoryConnMapper.insertOne(categoryConn);
        }
        for (OptionProductDto option : product.getOptionProductList()) {
            insert += optionProductMapper.insertOne(option);
        }
        return insert;
    }
}
