package com.example.gagooda_project.service;

import com.example.gagooda_project.StaticMethods;
import com.example.gagooda_project.dto.ImageDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.mapper.ImageMapper;
import com.example.gagooda_project.mapper.OptionProductMapper;
import com.example.gagooda_project.mapper.ReviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewServiceImp implements ReviewService {
    ReviewMapper reviewMapper;
    OptionProductMapper optionProductMapper;
    ImageMapper imageMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());


    public ReviewServiceImp(ReviewMapper reviewMapper, OptionProductMapper optionProductMapper, ImageMapper imageMapper) {
        this.reviewMapper = reviewMapper;
        this.optionProductMapper = optionProductMapper;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<ReviewDto> reviewList(String productCode) {
        return reviewMapper.listByProductCode(productCode);
    }

    @Override
    public ReviewDto selectOne(int reviewId) {
        return reviewMapper.findById(reviewId);
    }

    @Override
    public int insertOne(ReviewDto reviewDto) {
        return reviewMapper.insertOne(reviewDto);
    }

    @Override
    public int updateOne(ReviewDto dto) {
        return 0;
    }

    @Override
    public List<OptionProductDto> showOptionProduct(String productCode) {
        return optionProductMapper.listByProductCode(productCode);
    }

    @Override
    @Transactional
    public int register(List<MultipartFile> imgFileList, ReviewDto review, String imgPath) {
        try {
            if (reviewMapper.insertOne(review) <= 0) throw new Error("????????? ??????????????? ????????? ???????????????.");
            review.setImgCode("review" + review.getReviewId());
            if (reviewMapper.updateOne(review) <= 0) throw new Error("?????? ????????? ????????? ??????????????? ????????? ???????????????.");
            for (int imgFile = 0; imgFile < imgFileList.size(); imgFile++) {
                System.out.println("imgFileList imageFile : " + imgFileList.get(imgFile));
                if (imgFileList.get(imgFile) != null) {
                    ImageDto image = StaticMethods.parseIntoImage(imgFileList.get(imgFile), review.getImgCode(), imgPath + "/review", imgFile + 1);
                    if (image != null) {
                        if (imageMapper.insertOne(image) <= 0) throw new Error("????????? ???????????? ????????? ???????????????.");
                    } else {
                        log.info("????????? ????????? ????????????!!");
                    }
                }
            }
            return 1;
        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new Error("???????????? ??????");
        }
    }

    @Override
    @Transactional
    public int update(List<MultipartFile> imgFileList, ReviewDto review, String imgPath, List<String> imgToDeleteList) {
        List<ImageDto> imageDeleteList = new ArrayList<>();
        try {
            String content = review.getContent();
            int rate = review.getRate();
            review = reviewMapper.findById(review.getReviewId());
            review.setRate(rate);
            review.setContent(content);
            if (reviewMapper.updateOne(review) <= 0) throw new Error("????????? ??????????????? ????????? ???????????????.");

            // imgcode ??? seq ???????????? ???????????????
            if (imgToDeleteList != null) {
                for (String imgValue : imgToDeleteList) {
                    //imgToDeleteList: [review37/1] [imgCode/seq]
                    String imgCode = imgValue.split("/")[0];
                    int seq = Integer.parseInt(imgValue.split("/")[1]);
                    imageDeleteList.add(imageMapper.findByImgCodeAndSeq(imgCode, seq));
                    imageMapper.deleteByImgCodeAndSeq(imgCode, seq);
                }
            }
            // ??????????????? seq ????????? ????????? ????????? ????????????????????? ????????????
            List<ImageDto> imageList = imageMapper.listByImgCode(review.getImgCode());
            imageMapper.deleteByImgCode(review.getImgCode());

            // ????????? ????????? ??????
            for (int imgFile = 0; imgFile < imgFileList.size(); imgFile++) {
                ImageDto image = StaticMethods.parseIntoImage(imgFileList.get(imgFile), review.getImgCode(), imgPath + "/review", imgFile + 1);
                if (image != null) {
                    imageList.add(image);
                } else {
                    log.info("????????? ????????? ????????????!!");
                }
            }
            // ????????? ????????? ????????? seq ?????????
            for (int i = 0; i < imageList.size(); i++) {
                imageList.get(i).setSeq(i + 1);
                if (imageMapper.insertOne(imageList.get(i)) <= 0) throw new Error("??????");
            }

            for (ImageDto deletedImage : imageDeleteList) {
                File file = new File(imgPath + "/review/" + deletedImage.getImgPath());
                boolean del = file.delete();
                log.info(deletedImage.getImgPath() + " ?????? ??????: " + del);
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ReviewDto> showReviews(Map<String, Object> searchFilter) {
        if (searchFilter.get("searchWord") != null && !searchFilter.get("searchWord").equals("")) {
            String keyword = "%" + searchFilter.get("searchWord") + "%";
            searchFilter.put("searchWord", keyword);
        }
        if (searchFilter.get("startDate") != null
                && searchFilter.get("endDate") != null
                && searchFilter.get("startDate").equals(searchFilter.get("endDate"))) {
            String equalDate = searchFilter.get("startDate").toString() + "%";
            searchFilter.put("startDate", equalDate);
            searchFilter.put("endDate", equalDate);
        }
        PagingDto pagingDto = (PagingDto) searchFilter.get("paging");
        int totalRows = reviewMapper.count(searchFilter);
        pagingDto.setDirect("ASC");
        pagingDto.setRows(10);
        pagingDto.setTotalRows(totalRows);
        if (pagingDto.getOrderField() == null) {
            pagingDto.setOrderField("reg_date");
        }
        searchFilter.put("paging", pagingDto);
        return reviewMapper.pageAll(searchFilter);
    }

    @Override
    public int countByReviews(Map<String, Object> searchFilter) {
        return reviewMapper.count(searchFilter);
    }

    @Override
    public int countByProductCode(String productCode) {
        return reviewMapper.countByProductCode(productCode);
    }

    @Override
    @Transactional
    public int delete(ReviewDto review, int reviewId) {
        List<ImageDto> deleteImageList = imageMapper.listByImgCode(review.getImgCode());
        for (ImageDto deleteImage : deleteImageList) {
            File file = new File(deleteImage.getImgPath() + "/review/" + deleteImage.getImgPath());
            boolean del = file.delete();
        }
        reviewMapper.deleteOne(reviewId);
        return 1;
    }
}
