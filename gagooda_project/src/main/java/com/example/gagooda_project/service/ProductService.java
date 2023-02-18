package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> showProducts();
    ProductDto selectOne(String productCode);
    public int register(ProductDto product,
                        List<MultipartFile> imageFileList,
                        List<MultipartFile> infoImageFileList,
                        UserDto loginUser,
                        List<Integer> categoryIdList,
                        String imgPath);
    int remove(String productCode);
}
