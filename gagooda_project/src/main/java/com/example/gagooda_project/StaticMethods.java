package com.example.gagooda_project;

import com.example.gagooda_project.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class StaticMethods {
    public static ImageDto parseIntoImage(MultipartFile imgFile,
                                   String code,
                                   String imgPath,
                                   int seq) throws Exception {
        ImageDto image = null;
        String[] contentsTypes = Objects.requireNonNull(imgFile.getContentType()).split("/");
        if (contentsTypes[0].equals("image")) {
            String fileName = code + "_" + System.currentTimeMillis() + "_"
                    + (int) (Math.random() * 10000) + "." + contentsTypes[1];
            Path path = Paths.get(imgPath + "/" + fileName);
            imgFile.transferTo(path);
            image = new ImageDto();
            image.setImgPath(fileName);
            image.setImgCode(code);
            image.setSeq(seq);
        } else {
            throw new Exception("사진파일이 아닙니다.");
        }
        return image;
    }
}
