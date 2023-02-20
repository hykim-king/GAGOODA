package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class MainController {
    private CategoryService categoryService;

    public MainController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/mainpage.do")
    public String main(HttpServletRequest req){
        return "order/mainpage";
    }

    @GetMapping("/error.do")
    public String error(
            @SessionAttribute(required = false) Integer error,
            HttpSession session,
            Model model
    ) {
        if (error != null) {
            session.removeAttribute("error");
            model.addAttribute("error", error);
        }

        return "/errorHandler";
    }

    @GetMapping("/")
    public String main(
            @SessionAttribute(required = false) String msg,
            HttpSession session,
            Model model
    ) {
        if (msg != null) {
            System.out.println(msg);
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }

        // 카테고리 나열
        List<CategoryDto> firstCategories = null;
        try {
            firstCategories = categoryService.showCategoriesAt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (firstCategories != null) {
            model.addAttribute("categories", firstCategories);
        }

        return "index";
    }
}
