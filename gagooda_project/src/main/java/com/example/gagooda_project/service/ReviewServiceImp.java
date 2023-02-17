package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.mapper.ReviewMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImp implements ReviewService {
    ReviewMapper reviewMapper;

    public ReviewServiceImp(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public List<ReviewDto> reviewList(String productCode) {
        return reviewMapper.listByProductCode(productCode);
    }

    @Override
    public int insertOne(ReviewDto reviewDto) {
        return reviewMapper.insertOne(reviewDto);
    }

    @Override
    public List<ReviewDto> ListAll() { return reviewMapper.listAll(); }

    @Override
    public void updateOne(ReviewDto dto) {

    }
    @Override
    public int deleteById(int reviewId) { return reviewMapper.deleteById(reviewId); }
}
