package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OptionProductMapperTest {

    @Autowired
    OptionProductMapper optionProductMapper;
    @Autowired
    ProductMapper productMapper;

    @Test
    void listByProductCode() {
        optionProductMapper.listByProductCode("PDT011");
    }

    @Test
    void insertOne() {
        productMapper.deleteById("PDT011");

        ProductDto product = new ProductDto();
        product.setProductCode("PDT011");
        product.setPlace("GAGOODA");
        product.setPname("상품0011");
        product.setMargin(0.1);
        product.setSalesPc(10000);
        product.setSupplyPc(11000);
        product.setRot(0.1);
        product.setDeliveryPc(0);
        product.setImgCode("PDT011");
        product.setInfoImgCode("PDT011_INFO");
        productMapper.insertOne(product);

        OptionProductDto optionProduct = new OptionProductDto();
        optionProduct.setProductCode("PDT011");
        optionProduct.setOptionCode("PDT011_010");
        optionProduct.setStock(110);
        optionProduct.setOpname("상품002_11");
        optionProduct.setPrice(15000);
        optionProductMapper.insertOne(optionProduct);

        OptionProductDto optionProduct2 = new OptionProductDto();
        optionProduct2.setProductCode("PDT011");
        optionProduct2.setOptionCode("PDT011_011");
        optionProduct2.setStock(110);
        optionProduct2.setOpname("상품021_12");
        optionProduct2.setPrice(15000);
        optionProductMapper.insertOne(optionProduct2);
    }

    @Test
    void deleteByProductCode() {
        int num = optionProductMapper.deleteByProductCode("PDT011");
        System.out.println(num);
    }

    @Test
    void findById() {
        OptionProductDto optionProduct = optionProductMapper.findById("PDT011_011");
        System.out.println(optionProduct);
    }
}