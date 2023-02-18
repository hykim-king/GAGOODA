package com.example.gagooda_project.service;

import com.example.gagooda_project.dto.ImageDto;
import com.example.gagooda_project.mapper.ImageMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImp implements ImageService{
    private ImageMapper imageMapper;

    public ImageServiceImp(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @Override
    public int registerMultipartImage(MultipartFile imgFile,
                                      String imgPath,
                                      String code,
                                      int seq) throws Exception {
        int register = 0;
        String[] contentsTypes = Objects.requireNonNull(imgFile.getContentType()).split("/");
        if(contentsTypes[0].equals("image")) {
            String fileName=code+"_"+System.currentTimeMillis()+"_"
                    +(int)(Math.random()*10000)+"."+contentsTypes[1];
            Path path= Paths.get(imgPath+"/"+fileName);
            imgFile.transferTo(path);
            ImageDto image=new ImageDto();
            image.setImgPath(fileName);
            image.setImgCode(code);
            image.setSeq(seq);
            register = imageMapper.insertOne(image);
        } else {
            throw new Exception("사진파일이 아닙니다.");
        }
        return register;
    }

    @Override
    public int register(ImageDto image) {
        return imageMapper.insertOne(image);
    }

    @Override
    public int removeWithCode(String code) {
        return imageMapper.deleteByImgCode(code);
    }

    @Override
    public List<ImageDto> showImagesWithCode(String code) {
        return imageMapper.listByImgCode(code);
    }

    @Override
    public ImageDto showImageAt(String code, int seq) {
        return imageMapper.findByImgCodeAndSeq(code, seq);
    }


}
