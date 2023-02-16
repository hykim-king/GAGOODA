package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> reviewList(String productCode);
    List<ReviewDto> ListAll();
    int insertOne(ReviewDto dto);
    void updateOne(ReviewDto dto);
    int deleteById(int reviewId);


}
