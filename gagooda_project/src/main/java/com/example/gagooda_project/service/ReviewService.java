package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> ReviewList();

    public void insertReview(ReviewDto dto);

    public void deleteReview(ReviewDto dto);

    public void updateReview(ReviewDto dto);
}
