package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.CategoryService;
import com.example.gagooda_project.service.ProductService;
import com.example.gagooda_project.service.ZzimService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ZzimService zzimService;
    private final CartService cartService;

    public MainController(CategoryService categoryService,
                          ProductService productService,
                          ZzimService zzimService,
                          CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.zzimService = zzimService;
        this.cartService = cartService;
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

    @GetMapping("/admin/dev")
    public String devMain(
            @SessionAttribute(required = false) String msg,
            HttpSession session,
            Model model
    ) {
        if (msg != null) {
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
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        // 카테고리 나열
        List<CategoryDto> firstCategories = null;
        try {
            PagingDto paging = new PagingDto();
            // 보여줄 상품의 개수
            paging.setRows(10);
            // 정렬 기준
            paging.setOrderField("mod_date");
            // 정렬 방향
            paging.setDirect("DESC");
            List<ProductDto> recentProduct = productService.pagingProduct(paging, new HashMap<>());
            model.addAttribute("recentProduct", recentProduct);

            paging.setOrderField("order_cnt");
            List<ProductDto> orderedProductList = productService.pagingProduct(paging, new HashMap<>());
            model.addAttribute("orderedProductList", orderedProductList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (firstCategories != null) {
            model.addAttribute("categories", firstCategories);
        }
        return "mainpage";
    }

    @PostMapping("/search_results.do")
    public String searchResults(
            @RequestParam String searchWord,
            PagingDto paging,
            HttpSession session,
            HttpServletRequest req
    ) {
        if (paging.getOrderField() == null) paging.setOrderField("mod_date");
        String encodedParam = null;
        try {
            encodedParam = URLEncoder.encode(searchWord, "UTF-8");
            Map<String, String[]> paramMap = new HashMap<>(req.getParameterMap());
            if (paramMap.get("searchWord") != null) {
                String encodedWord = URLEncoder.encode(paramMap.get("searchWord")[0], "UTF-8");
                paramMap.put("searchWord", new String[]{encodedWord});
            }
            paging.setQueryString(req.getParameterMap());
            System.out.println(searchWord);
            if (searchWord.isBlank()) {
                return "redirect:/";
            } else {
                if (paging.getQueryString() != null) {
                    return "redirect:/search_results.do" + paging.getQueryString();
                } else {
                    return "redirect:/search_results.do?searchWord=" + encodedParam;
                }
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
            paging.setRows(16);
            if (paging.getOrderField() == null) paging.setOrderField("mod_date");
            paging.setQueryString(req.getParameterMap());
            Map<String, Object> map = new HashMap<>();
            map.put("searchWord", "'%" + searchWord + "%'");
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

    @GetMapping("/user_yes/quick_menu.do")
    public String quickMenu(
            @SessionAttribute UserDto loginUser,
            Model model
    ){
        try{
            List<ZzimDto> zzimList = zzimService.listByUserId(loginUser.getUserId());
            List<CartDto> cartList = cartService.cartList(loginUser.getUserId());
            model.addAttribute("zzimList", zzimList);
            model.addAttribute("cartList", cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "layout/quickZzimList";
    }
}
