package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> showProducts();
    ProductDto selectOne(String productCode);
    int register(ProductDto product);
    int remove(String productCode);
}
