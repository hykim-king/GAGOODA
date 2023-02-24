package com.example.gagooda_project.service;

import com.example.gagooda_project.StaticMethods;
import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.CategoryConnMapper;
import com.example.gagooda_project.mapper.ImageMapper;
import com.example.gagooda_project.mapper.OptionProductMapper;
import com.example.gagooda_project.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private ProductMapper productMapper;
    private CategoryConnMapper categoryConnMapper;
    private OptionProductMapper optionProductMapper;
    private ImageMapper imageMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
            option.setOptionCode(option.getProductCode() + "_" + option.getOptionCode());
            if (optionProductMapper.insertOne(option) <= 0) throw new Error();
        }
        try {
            for (int i = 0; i < imageFileList.size(); i++) {
                MultipartFile imgFile = imageFileList.get(i);
                ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getImgCode(),
                        imgPath+"/product", i + 1);
                if (imageMapper.insertOne(image) <= 0) throw new Error();
            }
            System.out.println("imageFileList 완료");
            for (int i = 0; i < infoImageFileList.size(); i++) {
                MultipartFile imgFile = infoImageFileList.get(i);
                ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getInfoImgCode(),
                        imgPath+"/product", i + 1);
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

    public List<ProductDto> pagingProduct(PagingDto paging, Map<String, Object> map) {
        int totalRows = productMapper.countForPaging(paging, map);
        paging.setTotalRows(totalRows);
        log.info("pagingDto pagination: "+paging);
        log.info("totalRows pagination: "+totalRows);
        log.info("map pagination: "+map);
        System.out.println(paging);
        return productMapper.pageForPaging(paging, map);
    }

    @Override
    public List<ProductDto> friMainList(String place) {
        return productMapper.mainListBySales(place);
    }

    @Override
    @Transactional
    public int modifyOne(
            ProductDto product,
            List<MultipartFile> imageFileList,
            List<MultipartFile> infoImageFileList,
            HashSet<String> categoryIdList,
            List<String> imgToDelete,
            UserDto loginUser,
            String imgPath,
            List<String> optionToDeleteList
    ) {
        // imgCode, infoImgCode 설정
        product.setImgCode(product.getProductCode());
        product.setInfoImgCode(product.getProductCode() + "_INFO");
        product.setModId(loginUser.getUserId());

        List<ImageDto> imagesToDelete = new ArrayList<>();
        try {
            // 기존 상품 정보 수정
            if (productMapper.updateOne(product) <= 0) throw new Error("상품 등록 중 오류");

            // 기존에 있던 상품과 카테고리 연결 삭제
            categoryConnMapper.deleteByProductCode(product.getProductCode());
            // categoryConnList 생성 및 product 에 설정
            for (String categoryId : categoryIdList) {
                if (!categoryId.isBlank()) {
                    CategoryConnDto categoryConn = new CategoryConnDto();
                    categoryConn.setProductCode(product.getProductCode());
                    categoryConn.setCategoryId(categoryId);
                    if (categoryConnMapper.insertOne(categoryConn) <= 0)
                        throw new Error("상품 카테고리 연결 등록 중 오류");
                }
            }

            // 삭제 할 옵션들 삭제
            for (String optionCode:optionToDeleteList) {
                if (optionProductMapper.deleteById(optionCode)<=0) throw new Error("옵션 상품 삭제 중 오류");
                product.getOptionUpdateList().removeIf(optionProduct -> optionCode.equals(optionProduct.getOptionCode()));
            }

            // 수정 할 옵션들 수정
            for (OptionProductDto optionProduct:product.getOptionUpdateList()) {
                optionProduct.setPrice(product.getSalesPc());
                if (optionProductMapper.updateOne(optionProduct)<=0) throw new Error("옵션 상품 수정 중 오류");
            }

            // 추가할 옵션들 추가
            for (OptionProductDto option : product.getOptionProductList()) {
                // 옵션 코드 재설정
                option.setOptionCode(option.getProductCode() + "_" + option.getOptionCode());
                if (optionProductMapper.insertOne(option) <= 0) throw new Error("옵션 추가 중 오류");
            }

            // 삭제 될 image데이터 삭제
            for (String imageCodeSeq : imgToDelete) {
                String code = imageCodeSeq.split("/")[0];
                int seq = Integer.parseInt(imageCodeSeq.split("/")[1]);
                imagesToDelete.add(imageMapper.findByImgCodeAndSeq(code,seq));
                imageMapper.deleteByImgCodeAndSeq(code, seq);
            }

            // 남은 이미지를 불러오고, DB 속 이미지들 삭제
            List<ImageDto> imageList = imageMapper.listByImgCode(product.getImgCode());
            imageMapper.deleteByImgCode(product.getImgCode());

            // 이미지 파일들 ImageDto 객체로 변환해서 list에 담기
            for (int i = 0; i < imageFileList.size(); i++) {
                MultipartFile imgFile = imageFileList.get(i);
                ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getImgCode(),
                        imgPath+"/product", i + 1);
                imageList.add(image);
            }

            // imageList에 있는 모든 이미지들을 순번을 다시 매겨서 DB에 저장
            for (int i = 0; i < imageList.size(); i++) {
                imageList.get(i).setSeq(i+1);
                if(imageMapper.insertOne(imageList.get(i)) <= 0) throw new Error("상품 이미지 등록 중 오류");
            }

            List<ImageDto> infoImageList = imageMapper.listByImgCode(product.getInfoImgCode());
            imageMapper.deleteByImgCode(product.getInfoImgCode());
            for (int i = 0; i < infoImageFileList.size(); i++) {
                MultipartFile imgFile = infoImageFileList.get(i);
                ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getInfoImgCode(),
                        imgPath+"/product", i + 1);
                infoImageList.add(image);
            }
            for (int i = 0; i < infoImageList.size(); i++) {
                infoImageList.get(i).setSeq(i+1);
                if(imageMapper.insertOne(infoImageList.get(i)) <= 0) throw new Error("상품 정보 이미지 등록 중 오류");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }

        for (ImageDto deletedImage : imagesToDelete) {
            File file = new File(imgPath+"/product/"+deletedImage.getImgPath());
            boolean del = file.delete();
            log.info(deletedImage.getImgPath()+" 삭제 완료: "+del);
        }

        return 1;
    }
}
/*
SELECT COUNT(*) FROM
(SELECT product.*
        FROM product
        LEFT JOIN category_conn cc
        ON product.product_code = cc.product_code
        WHERE cc.category_id = 5
        GROUP BY product.product_code) as db;
 */

