package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
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
    public ReviewDto selectOne(int reviewId) {
        return reviewMapper.findById(reviewId);
    }

    @Override
    public int insertOne(ReviewDto reviewDto) {
        return reviewMapper.insertOne(reviewDto);
    }

    @Override
    public int updateOne(ReviewDto dto) {
        return 0;
    }

    @Override
    public int remove(int reviewId) {
        return reviewMapper.deleteOne(reviewId);
    }

}
