package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/levelone.do")
    public String levelOne(
            Model model
    ) {
        try {
            List<CategoryDto> levelOneList = categoryService.showCategoriesAt(1);
            model.addAttribute(levelOneList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product/register";
    }

    @PostMapping("/levelone.do")
    public String levelOne() {
        return "";
    }
}
