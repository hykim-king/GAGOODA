package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<ReviewDto> listByProductCode (String productCode);

//    List<ReviewDto> pageByProductId (String productId);

    List<ReviewDto> listAll();

//    List<ReviewDto> pageAll();

    ReviewDto findById(int reviewId);

    int insertOne(ReviewDto review);
}
