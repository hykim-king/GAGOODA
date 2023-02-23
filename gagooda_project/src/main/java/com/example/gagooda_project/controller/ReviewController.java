package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ReviewService;
import com.example.gagooda_project.service.ReviewServiceImp;
import com.fasterxml.jackson.datatype.jdk8.OptionalSerializer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    ReviewService reviewService;
    @Value("${img.upload.path}")
    private String imgPath;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
//        this.optionService = optionService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @RequestParam(name = "productCode", required = false) String productCode) {
        List<ReviewDto> reviewList = reviewService.reviewList(productCode);
        model.addAttribute("reviewList", reviewList);
        System.out.println(reviewList);
        return "/review/list";
    }

    @GetMapping("/user_yes/{productCode}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable(required = true, name = "productCode") String productCode,
                           Model model
    ) {
        List<OptionProductDto> optionProductList = reviewService.showOptionProduct(productCode);
        model.addAttribute("optionProductList",optionProductList);
        System.out.println(optionProductList+"@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#");
        return "/review/register";
    }

    // localhost:8888/review/user_yes/register.do?optionCode=HA00G01EEU_GY

    @PostMapping("/user_yes/register.do")
    public String register(ReviewDto review,
                           Model model,
                           HttpSession session,
                           @RequestParam(name = "imgFile") List<MultipartFile> imgFileList,
                           @SessionAttribute UserDto loginUser) {
        int register = 0;
        if (loginUser.getUserId() == review.getUserId()) {
            try {
                String user = Integer.toString(loginUser.getUserId());
                model.addAttribute("user", loginUser.getUserId());
                register = reviewService.insertOne(review);
                System.out.println(imgFileList+"@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (register > 0) {
            return "redirect:/review/list.do";
//            return "redirect:/review/list.do?productCode=" + review.getProductCode();
        } else {
            return "redirect:/review/list.do";
//            return "redirect:/review/user_yes/register.do?productCode="+review.getProductCode();
        }
    }

    @GetMapping("/remove.do")
    public String remove( @RequestParam(name = "reviewId") int reviewId) {
        int delete = 0;
        try {
            delete = reviewService.remove(reviewId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/review/list.do";
    }
}

/*
    @GetMapping("/matchingRegister.do")
    public String register(@SessionAttribute UserDto loginInfo) {
        return "/matching/matchingRegister";
    }
    @PostMapping("/matchingRegister.do")
    public String register(
            MatchingDto matching,
            @RequestParam(required = false, name = "imgFile")
            MultipartFile[] imgFiles,
            @SessionAttribute UserDto loginInfo
    ) {
        int register = 0;
        try {
            List<MatchingImgDto> matchingImgList = new ArrayList<MatchingImgDto>();
            for (MultipartFile imgFile : imgFiles) {
                if (imgFile != null && !imgFile.isEmpty()) {
                    String[] contentsTypes = imgFile.getContentType().split("/");
                    if (contentsTypes[0].equals("image")) {
                        try {
                            String fileName = "matching_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000) + "." + contentsTypes[1];
                            Path path = Paths.get(imgPath + "/" + fileName);
                            imgFile.transferTo(path);
                            MatchingImgDto matchingImg = new MatchingImgDto();
                            matchingImg.setImgPath(fileName);
                            matchingImgList.add(matchingImg);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
            matching.setMatchingImgList(matchingImgList);
            System.out.println(matching);
            register = matchingService.register(matching);
            System.out.println(register);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (register > 0) {
            return "redirect:/matching/matchingDetail.do?matchingNo=" + matching.getMatchingNo();
        } else {
            return "redirect:/matching/matchingMain";
        }
    }
}
 */