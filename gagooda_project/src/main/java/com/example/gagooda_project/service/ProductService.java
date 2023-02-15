package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.CategoryConnDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<ProductDto> showProducts();
    ProductDto selectOne(String productCode);
}
