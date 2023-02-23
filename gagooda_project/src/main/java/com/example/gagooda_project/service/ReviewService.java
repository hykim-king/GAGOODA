package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> reviewList(String productCode);
    ReviewDto selectOne(int reviewId);
    int insertOne(ReviewDto dto);
    int updateOne(ReviewDto dto);
    int remove(int reviewId);


}
