package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.ReviewDto;
import com.example.gagooda_project.service.ReviewService;
import com.example.gagooda_project.service.ReviewServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { this.reviewService= reviewService; }

    @GetMapping("/list.do")
    public String list(Model model,
                       @RequestParam(name = "productCode", required = false) String productCode) {
        List<ReviewDto> reviewList = reviewService.reviewList(productCode);
        model.addAttribute("reviewList", reviewList);
        System.out.println(reviewList);
        return "review/list";
    }


}
