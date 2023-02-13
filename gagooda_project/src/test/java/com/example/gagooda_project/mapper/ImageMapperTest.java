package com.example.gagooda_project.mapper;

import com.example.gagooda_project.dto.ImageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ImageMapperTest {
    @Autowired
    private ImageMapper imageMapper;

    @Test
    void insertOne() {
        ImageDto image = new ImageDto();
        image.setImgCode("PSDLD-010");
        image.setImgPath("asdfsdafd.png");
        image.setSeq(1);
        imageMapper.insertOne(image);
    }
    @Test
    void deleteByImgCode() {
        int deleteImgCode = imageMapper.deleteByImgCode("PSDLD-010");
    }
    @Test
    void findByImgCodeAndSeq() {
        ImageDto findImgCodeSeq = imageMapper.findByImgCodeAndSeq("PDT001",1);
    }
    @Test
    void listByImgCode() {
        List<ImageDto> allImage = imageMapper.listByImgCode("PDT001");
        System.out.println(allImage);
    }
}
