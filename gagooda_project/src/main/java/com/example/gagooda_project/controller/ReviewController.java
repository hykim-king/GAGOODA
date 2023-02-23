package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ReviewService;
import com.example.gagooda_project.service.ReviewServiceImp;
import com.fasterxml.jackson.datatype.jdk8.OptionalSerializer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    ReviewService reviewService;
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

    @GetMapping("/user_yes/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            @RequestParam(required = true, name = "optionCode") String optionCode,
            Model model
    ) {
//        OptionProductDto optionProduct = optionService.selectOne(optionCode);
//        model.addAttribute("optionProduct", optionProduct);
        model.addAttribute("optionCode", optionCode);
        return "/review/register";
    }

    // localhost:8888/review/user_yes/register.do?optionCode=HA00G01EEU_GY

    @PostMapping("/user_yes/register.do")
    public String register(ReviewDto review,
                           Model model,
                           @SessionAttribute UserDto loginUser) {
        int register = 0;
        if (loginUser.getUserId() == review.getUserId()) {
            try {
                String user = Integer.toString(loginUser.getUserId());
                model.addAttribute("user", loginUser.getUserId());
                register = reviewService.insertOne(review);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (register > 0) {
            return "redirect:/review/list.do?productCode=" + review.getProductCode();
        } else {
            return "redirect:/review/user_yes/register.do?productCode="+review.getProductCode();
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

