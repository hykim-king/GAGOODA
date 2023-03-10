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
        // imgCode, infoImgCode ??????
        product.setImgCode(product.getProductCode());
        product.setInfoImgCode(product.getProductCode() + "_INFO");
        product.setRegId(loginUser.getUserId());
        product.setModId(loginUser.getUserId());
        if (productMapper.insertOne(product) <= 0) throw new Error();

        // categoryConnList ?????? ??? product ??? ??????
        for (String categoryId : categoryIdList) {
            if (!categoryId.isBlank()) {
                CategoryConnDto categoryConn = new CategoryConnDto();
                categoryConn.setProductCode(product.getProductCode());
                categoryConn.setCategoryId(categoryId);
                if (categoryConnMapper.insertOne(categoryConn) <= 0) throw new Error();
            }
        }
        for (OptionProductDto option : product.getOptionProductList()) {
            // ?????? ?????? ?????????
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
            System.out.println("imageFileList ??????");
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
        // imgCode, infoImgCode ??????
        product.setImgCode(product.getProductCode());
        product.setInfoImgCode(product.getProductCode() + "_INFO");
        product.setModId(loginUser.getUserId());

        List<ImageDto> imagesToDelete = new ArrayList<>();
        try {
            // ?????? ?????? ?????? ??????
            if (productMapper.updateOne(product) <= 0) throw new Error("?????? ?????? ??? ??????");

            categoryConnMapper.deleteByProductCode(product.getProductCode());
            // categoryConnList ?????? ??? product ??? ??????
            if (categoryIdList != null) {
                // ????????? ?????? ????????? ???????????? ?????? ??????
                for (String categoryId : categoryIdList) {
                    if (!categoryId.isBlank()) {
                        CategoryConnDto categoryConn = new CategoryConnDto();
                        categoryConn.setProductCode(product.getProductCode());
                        categoryConn.setCategoryId(categoryId);
                        if (categoryConnMapper.insertOne(categoryConn) <= 0)
                            throw new Error("?????? ???????????? ?????? ?????? ??? ??????");
                    }
                }
            } else {
                log.info("????????? ?????? ??????????????? ????????????.");
            }

            // ?????? ??? ????????? ??????
            if (optionToDeleteList != null) {
                for (String optionCode:optionToDeleteList) {
                    if (optionProductMapper.deleteById(optionCode)<=0) throw new Error("?????? ?????? ?????? ??? ??????");
                    product.getOptionUpdateList().removeIf(optionProduct -> optionCode.equals(optionProduct.getOptionCode()));
                }
            }

            // ?????? ??? ????????? ??????
            if (product.getOptionUpdateList() != null) {
                for (OptionProductDto optionProduct:product.getOptionUpdateList()) {
                    optionProduct.setPrice(product.getSalesPc());
                    if (optionProductMapper.updateOne(optionProduct)<=0) throw new Error("?????? ?????? ?????? ??? ??????");
                }
            }

            // ????????? ????????? ??????
            if (product.getOptionProductList() != null) {
                for (OptionProductDto option : product.getOptionProductList()) {
                    // ?????? ?????? ?????????
                    option.setOptionCode(option.getProductCode() + "_" + option.getOptionCode());
                    if (optionProductMapper.insertOne(option) <= 0) throw new Error("?????? ?????? ??? ??????");
                }
            }

            // ?????? ??? image????????? ??????
            if (imgToDelete != null) {
                for (String imageCodeSeq : imgToDelete) {
                    String code = imageCodeSeq.split("/")[0];
                    int seq = Integer.parseInt(imageCodeSeq.split("/")[1]);
                    imagesToDelete.add(imageMapper.findByImgCodeAndSeq(code,seq));
                    imageMapper.deleteByImgCodeAndSeq(code, seq);
                }
            }

            // ?????? ???????????? ????????????, DB ??? ???????????? ??????
            List<ImageDto> imageList = imageMapper.listByImgCode(product.getImgCode());
            imageMapper.deleteByImgCode(product.getImgCode());

            if (imageFileList != null) {
                // ????????? ????????? ImageDto ????????? ???????????? list??? ??????
                for (int i = 0; i < imageFileList.size(); i++) {
                    MultipartFile imgFile = imageFileList.get(i);
                    ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getImgCode(),
                            imgPath+"/product", i + 1);
                    imageList.add(image);
                }
            }

            // imageList??? ?????? ?????? ??????????????? ????????? ?????? ????????? DB??? ??????
            for (int i = 0; i < imageList.size(); i++) {
                imageList.get(i).setSeq(i+1);
                if(imageMapper.insertOne(imageList.get(i)) <= 0) throw new Error("?????? ????????? ?????? ??? ??????");
            }

            List<ImageDto> infoImageList = imageMapper.listByImgCode(product.getInfoImgCode());
            imageMapper.deleteByImgCode(product.getInfoImgCode());

            if (infoImageFileList != null) {
                for (int i = 0; i < infoImageFileList.size(); i++) {
                    MultipartFile imgFile = infoImageFileList.get(i);
                    ImageDto image = StaticMethods.parseIntoImage(imgFile, product.getInfoImgCode(),
                            imgPath+"/product", i + 1);
                    infoImageList.add(image);
                }
            }
            for (int i = 0; i < infoImageList.size(); i++) {
                infoImageList.get(i).setSeq(i+1);
                if(imageMapper.insertOne(infoImageList.get(i)) <= 0) throw new Error("?????? ?????? ????????? ?????? ??? ??????");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error();
        }

        for (ImageDto deletedImage : imagesToDelete) {
            File file = new File(imgPath+"/product/"+deletedImage.getImgPath());
            boolean del = file.delete();
            log.info(deletedImage.getImgPath()+" ?????? ??????: "+del);
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

