package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.CategoryConnService;
import com.example.gagooda_project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CategoryConnService categoryConnService;
    public ProductController(ProductService productService,
                             CategoryConnService categoryConnService) {
        this.productService = productService;
        this.categoryConnService = categoryConnService;
    }

    @Value("${img.upload.path}")
    private String imgPath;

    @GetMapping("/list.do")
    public String list(
            Model model,
            HttpSession session,
            @SessionAttribute(required = false) String msg
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            System.out.println(msg);
        }
        List<ProductDto> productList = null;
        try {
            productList = productService.showProducts();
            productList = productList.subList(0, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (productList != null) {
            model.addAttribute("productList", productList);
            return "/product/list";
        } else {
            session.setAttribute("msg", "상품들을 가져올 수 없습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/{categoryId}/list.do")
    public String list(
            Model model,
            HttpSession session,
            @SessionAttribute(required = false) String msg,
            @PathVariable int categoryId
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            System.out.println(msg);
        }
        List<CategoryConnDto> categoryConnList = null;
        try {
            categoryConnList = categoryConnService.categoryProducts(categoryId);
            categoryConnList = categoryConnList.subList(0, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (categoryConnList != null) {
            model.addAttribute("categoryConnList", categoryConnList);
            return "/product/category_list";
        } else {
            session.setAttribute("msg", "상품들을 가져올 수 없습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/{productCode}/detail.do")
    public String detail (
            @PathVariable String productCode,
            HttpSession session,
            Model model
    ) {
        ProductDto product = null;
        try {
            product = productService.selectOne(productCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (product != null) {
            model.addAttribute("product", product);
            return "/product/detail";
        } else {
            session.setAttribute("msg", "데이터를 찾을 수 없습니다.");
            return "redirect:/product/list.do";
        }
    }

    @GetMapping("/admin/register.do")
    public String insert(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String msg,
            HttpSession session,
            Model model
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "/product/register";
    }

    @PostMapping("/admin/register.do")
    public String insert(
            @SessionAttribute UserDto loginUser,
            HttpSession session,
            ProductDto product,
            @RequestParam(name = "categoryId") List<Integer> categoryIdList
    ) {
        System.out.println(product);
        for (int categoryId : categoryIdList) {
            CategoryConnDto categoryConn = new CategoryConnDto();
            categoryConn.setCategoryId(categoryId);
            categoryConn.setProductCode(product.getProductCode());
            System.out.println(categoryConn);
        }
        System.out.println(product.getImageList());
        System.out.println(product.getInfoImgCode());
        System.out.println(product.getCategoryConnList());
        System.out.println(product.getOptionProductList());
        return "redirect:/";
    }

}
