package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewMapperTest {
    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    void listByProductId() {
//        reviewMapper.listByProductId("1");
    }

    @Test
    void pageByProductId() {
    }

    @Test
    void listAll() {
    }

    @Test
    void pageAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void insertOne(){
        ReviewDto review = new ReviewDto();
        review.setUserId(1);
        review.setOptionCode("PDT001_001");
        review.setProductCode("PDT001");
        review.setRate(4.5);
        review.setContent("test");
        review.setRegDate(new Date());
        review.setImgCode("review_1");
        reviewMapper.insertOne(review);
    }
}