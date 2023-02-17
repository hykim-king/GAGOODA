package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    int register(ImageDto image);
    int removeWithCode(String code);
    List<ImageDto> showImagesWithCode(String code);
    ImageDto showImageAt(String code, int seq);
    int registerMultipartImage(MultipartFile imgFile, String imgPath, String code, int seq) throws Exception;
}
