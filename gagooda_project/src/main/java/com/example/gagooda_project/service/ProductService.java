package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDto> showProducts();
    ProductDto selectOne(String productCode);
    int register(ProductDto product,
                        List<MultipartFile> imageFileList,
                        List<MultipartFile> infoImageFileList,
                        UserDto loginUser,
                        HashSet<String> categoryIdList,
                        String imgPath);
    int remove(String productCode);

    List<ProductDto> pagingProduct(PagingDto paging, Map<String, Object> map);
}
