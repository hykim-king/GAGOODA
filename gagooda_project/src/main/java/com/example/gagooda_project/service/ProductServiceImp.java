package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import com.example.gagooda_project.mapper.ImageMapper;
import com.example.gagooda_project.mapper.OptionProductMapper;
import com.example.gagooda_project.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.HashAttributeSet;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImp implements ProductService{
    private ProductMapper productMapper;
    private CategoryConnMapper categoryConnMapper;
    private OptionProductMapper optionProductMapper;
    private ImageMapper imageMapper;

    public ProductServiceImp(ProductMapper productMapper,
                             CategoryConnMapper categoryConnMapper,
                             OptionProductMapper optionProductMapper,
                             ImageMapper imageMapper) {
        this.productMapper = productMapper;
        this.categoryConnMapper = categoryConnMapper;
        this.optionProductMapper = optionProductMapper;
        this.imageMapper = imageMapper;
    }

    public List<ProductDto> showProducts() {
        return productMapper.listAll();
    }

    public ProductDto selectOne(String productCode) {
        return productMapper.findById(productCode);
    }

    @Transactional
    public int register(ProductDto product,
                        List<MultipartFile> imageFileList,
                        List<MultipartFile> infoImageFileList,
                        UserDto loginUser,
                        HashSet<String> categoryIdList,
                        String imgPath) {
        // imgCode, infoImgCode 설정
        product.setImgCode(product.getProductCode());
        product.setInfoImgCode(product.getProductCode() + "_INFO");
        product.setRegId(loginUser.getUserId());
        product.setModId(loginUser.getUserId());
        if (productMapper.insertOne(product) <= 0) throw new Error();

        // categoryConnList 생성 및 product 에 설정
        for (String categoryId : categoryIdList) {
            if (!categoryId.isBlank()) {
                CategoryConnDto categoryConn = new CategoryConnDto();
                categoryConn.setProductCode(product.getProductCode());
                categoryConn.setCategoryId(categoryId);
                if (categoryConnMapper.insertOne(categoryConn) <= 0) throw new Error();
            }
        }
        for (OptionProductDto option : product.getOptionProductList()) {
            // 옵션 코드 재설정
            option.setOptionCode(option.getProductCode()+"_"+option.getOptionCode());
            if (optionProductMapper.insertOne(option) <= 0) throw new Error();
        }
        try {
            for (int i = 0; i < imageFileList.size(); i++) {
                MultipartFile imgFile = imageFileList.get(i);
                ImageDto image = parseIntoImage(imgFile, product.getImgCode(),
                        imgPath+"/product", i+1);
                if (imageMapper.insertOne(image) <= 0) throw new Error();
            }
            System.out.println("imageFileList 완료");
            for (int i = 0; i < infoImageFileList.size(); i++) {
                MultipartFile imgFile = infoImageFileList.get(i);
                ImageDto image = parseIntoImage(imgFile, product.getInfoImgCode(),
                        imgPath+"/product", i+1);
                if (imageMapper.insertOne(image) <= 0) throw new Error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }
        return 1;
    }

    public int remove(String productCode) {
        return productMapper.deleteById(productCode);
    }

    public ImageDto parseIntoImage(MultipartFile imgFile,
                                   String code,
                                   String imgPath,
                                   int seq) throws Exception {
        ImageDto image=null;
        String[] contentsTypes = Objects.requireNonNull(imgFile.getContentType()).split("/");
        if(contentsTypes[0].equals("image")) {
            String fileName=code+"_"+System.currentTimeMillis()+"_"
                    +(int)(Math.random()*10000)+"."+contentsTypes[1];
            Path path= Paths.get(imgPath+"/"+fileName);
            imgFile.transferTo(path);
            image = new ImageDto();
            image.setImgPath(fileName);
            image.setImgCode(code);
            image.setSeq(seq);
        } else {
            throw new Exception("사진파일이 아닙니다.");
        }
        return image;
    }

    public List<ProductDto> pagingProduct(PagingDto paging) {
        int totalRows = productMapper.count(paging);
        paging.setTotalRows(totalRows);
        System.out.println(paging);
//        return productMapper.pageSearch(paging);
        return null;
    }

    @Override
    public List<ProductDto> friMainList(String place) {
        return productMapper.mainListBySales(place);
    }
}

