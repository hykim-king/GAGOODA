package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CategoryDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.service.CategoryService;
import com.example.gagooda_project.service.ProductInquiryService;
import com.example.gagooda_project.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private CategoryService categoryService;
    private ProductService productService;

    public MainController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/mainpage.do")
    public String main(HttpServletRequest req,
                       Model model){
        try{
            PagingDto recentPaging = new PagingDto();
            // 보여줄 상품의 개수
            recentPaging.setRows(10);
            // 정렬 기준
            recentPaging.setOrderField("mod_date");
            // 정렬 방향
            recentPaging.setDirect("DESC");
            List<ProductDto> recentProduct = productService.pagingProduct(recentPaging, new HashMap<>());
            model.addAttribute("recentProduct",recentProduct);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    @GetMapping("/dev")
    public String devMain(
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
        return "order/mainpage";
    }

    @PostMapping("/search_results.do")
    public String searchResults(
            @RequestParam String searchWord,
            HttpSession session
    ) {
        String encodedParam = null;
        try {
            encodedParam = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println(searchWord);
            if(searchWord.isBlank()) {
                return "redirect:/";
            } else {
                return "redirect:/search_results.do?searchWord="+encodedParam;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            session.setAttribute("msg", "검색 결과를 가져오는데에 문제가 있었습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/search_results.do")
    public String searchResults(
            @RequestParam String searchWord,
            Model model,
            PagingDto paging,
            HttpServletRequest req,
            @SessionAttribute(required = false) String msg,
            HttpSession session
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try {
            paging.setRows(20);
            if (paging.getOrderField() == null) paging.setOrderField("mod_date");
            paging.setQueryString(req.getParameterMap());
            Map<String, Object> map = new HashMap<>();
            map.put("searchWord", "'%"+searchWord+"%'");
            List<ProductDto> productList = productService.pagingProduct(paging, map);
            model.addAttribute("paging", paging);
            model.addAttribute("productList", productList);
            model.addAttribute("searchWord", searchWord);
            return "search_result";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
}
