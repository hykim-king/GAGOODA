package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {
    int insertOne(ImageDto image);

    int deleteByImgCode(String imgCode);

    ImageDto findByImgCodeAndSeq(String imgCode, int seq);

    List<ImageDto> listByImgCode(String imgCode);
}
