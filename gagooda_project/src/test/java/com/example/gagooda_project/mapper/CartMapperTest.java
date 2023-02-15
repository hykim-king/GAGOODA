package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.CartDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Test
    void insertOne() {
        CartDto cart = new CartDto();
        cart.setCnt(1);
        cart.setProductCode("PDT001");
        cart.setOptionCode("PDT001_001");
        cart.setUserId(10);
        cartMapper.insertOne(cart);
        log.info(cart.toString());
    }

    @Test
    void deleteById() {
        log.info(cartMapper.deleteById(3)+"");
    }

    @Test
    void deleteByUserId() {
        log.info(cartMapper.deleteByUserId(1)+"");
    }

    @Test
    void countByUserId() {
        cartMapper.countByUserId(2);
    }

    @Test
    void listByUserId() {
        log.info(cartMapper.listByUserId(1).toString());
    }

    @Test
    void updateOne() {
        CartDto cart = cartMapper.findByUserIdAndOptionCode(2, "PDT002_002");
        int count = cart.getCnt();
        cart.setCnt(count+1);
        cartMapper.updateOne(cart);

    }
//    @Test
//    void updateOne() {
//        CartDto cartDto = new CartDto();
//        cartDto.setCnt(10);
//        cartDto.setUserId(1);
//        cartDto.setOptionCode("PDT002_001");
//        cartMapper.updateOne(cartDto);
//        log.info(cartMapper.findByUserIdAndOptionCode(1,"PDT002_001")+"");
//    }

    @Test
    void findByUserIdAndOptionCode() {
        log.info(cartMapper.findByUserIdAndOptionCode(2, "PDT002_002").toString());
    }
}