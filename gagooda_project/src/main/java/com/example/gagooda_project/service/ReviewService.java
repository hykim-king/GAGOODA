package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ReviewService {
    List<ReviewDto> reviewList(String productCode);

    ReviewDto selectOne(int reviewId);

    int insertOne(ReviewDto dto);

    int updateOne(ReviewDto dto);

    List<OptionProductDto> showOptionProduct(String productCode);

    @Transactional
    int register(List<MultipartFile> imgFileList, ReviewDto review, String imgPath);

    @Transactional
    int update(List<MultipartFile> imgFileList, ReviewDto review, String imgPath, List<String> imgToDeleteList);

    List<ReviewDto> showReviews(Map<String, Object> searchFilter);

    int countByReviews(Map<String, Object> searchFilter);

    @Transactional
    int delete(ReviewDto review, int reviewId);
}
