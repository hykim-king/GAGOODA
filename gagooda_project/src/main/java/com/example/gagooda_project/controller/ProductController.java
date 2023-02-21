package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.*;
import com.sun.jdi.event.ExceptionEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryConnService categoryConnService;
    private final CategoryService categoryService;
    private final CommonCodeService commonCodeService;

    public ProductController(ProductService productService,
                             CategoryConnService categoryConnService,
                             CategoryService categoryService,
                             CommonCodeService commonCodeService) {
        this.productService = productService;
        this.categoryConnService = categoryConnService;
        this.categoryService = categoryService;
        this.commonCodeService = commonCodeService;
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
            model.addAttribute("msg", msg);
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
            @PathVariable int categoryId,
            PagingDto paging,
            HttpServletRequest req
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
            System.out.println(msg);
        }
        paging.setRows(16);
        if (paging.getOrderField() == null) paging.setOrderField("mod_date");
        paging.setQueryString(req.getParameterMap());
        model.addAttribute("paging", paging);

        Map<String, Object> map = new HashMap<>();
        List<Integer> categoryIdList = new ArrayList<>();
        categoryIdList.add(categoryId);
        map.put("categoryIdList", categoryIdList);
        try {
            List<ProductDto> productList = productService.pagingProduct(paging, map);
            model.addAttribute("productList", productList);
            return "/product/category_list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "상품들을 가져올 수 없습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/{productCode}/detail.do")
    public String detail(
            @PathVariable String productCode,
            HttpSession session,
            Model model,
            @SessionAttribute(required = false)  String msg,
            HttpServletRequest request
    ) {
        System.out.println("msg " + msg);
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        ProductDto product = null;
        try {
            product = productService.selectOne(productCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (product != null) {
            model.addAttribute("product", product);
            session.setAttribute("getUri", request.getRequestURI());
            return "/product/detail";
        } else {
            session.setAttribute("msg", "데이터를 찾을 수 없습니다.");
            return "redirect:/product/list.do";
        }
    }

    @GetMapping("/admin/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String msg,
            HttpSession session,
            Model model
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
            System.out.println(msg);
        }
        if (loginUser.getGDet().equals("g1")) {
            try {
                List<CategoryDto> levelOne = categoryService.showCategoriesAt(1);
                List<CategoryDto> levelTwo = categoryService.showCategoriesAt(2);
                List<CategoryDto> levelThree = categoryService.showCategoriesAt(3);
                List<CommonCodeDto> productDet = commonCodeService.showDets("p");
                model.addAttribute("levelOne", levelOne);
                model.addAttribute("levelTwo", levelTwo);
                model.addAttribute("levelThree", levelThree);
                model.addAttribute("productDet", productDet);
                return "/product/register";
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
                return "redirect:/";
            }
        } else {
            session.setAttribute("msg", "관리자만 상품을 등록 할 수 있습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/admin/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            HttpSession session,
            ProductDto product,
            @RequestParam(name = "category") HashSet<String> categoryIdList,
            @RequestParam(name = "imageFile") List<MultipartFile> imageFileList,
            @RequestParam(name = "infoImageFile") List<MultipartFile> infoImageFileList
    ) {
        if (loginUser.getGDet().equals("g1")) {
            int insert = 0;
            try {
                // product, categoryConn 등록
                insert = productService.register(product, imageFileList, infoImageFileList,
                        loginUser, categoryIdList, imgPath);
                System.out.println("insert:" + insert);
            } catch (Exception e) {
                e.printStackTrace();
                insert = 0;
            }
            if (insert > 0) {
                return "redirect:/product/" + product.getProductCode() + "/detail.do";
            } else {
                session.setAttribute("msg", "등록 중 오류가 났습니다.");
                return "redirect:/product/admin/register.do";
            }
        } else {
            session.setAttribute("msg", "관리자만 상품을 등록 할 수 있습니다.");
            return "redirect:/";
        }
    }

    @GetMapping("/admin/product_list.do")
    public String productList (
            @RequestParam(required = false, name = "categoryId") String categoryId,
            @RequestParam(required = false, name = "catDet") List<String> catDetList,
            @RequestParam(required = false) String searchWord,
            Model model,
            PagingDto paging,
            HttpServletRequest req,
            HttpSession session
    ){
        paging.setRows(20);
        if (paging.getOrderField() == null) paging.setOrderField("mod_date");
        paging.setQueryString(req.getParameterMap());
        model.addAttribute("paging", paging);
        Map<String, Object> map = new HashMap<>();
        if (categoryId != null) {
            map.put("categoryId", categoryId);
            model.addAttribute("categoryId", categoryId);
        }
        if (catDetList != null) {
            map.put("catDetList", catDetList);
        }
        if (searchWord != null) {
            map.put("searchWord", "'%"+searchWord+"%'");
        }
        try {
            List<ProductDto> productList = productService.pagingProduct(paging, map);
            model.addAttribute("productList", productList);

            Map<String, String> detDict = commonCodeService.showNames("p");
            model.addAttribute("detDict", detDict);

            List<CategoryDto> levelOne = categoryService.showCategoriesAt(1);
            List<CategoryDto> levelTwo = categoryService.showCategoriesAt(2);
            List<CategoryDto> levelThree = categoryService.showCategoriesAt(3);
            model.addAttribute("levelOne", levelOne);
            model.addAttribute("levelTwo", levelTwo);
            model.addAttribute("levelThree", levelThree);

            List<CategoryDto> categoryList = categoryService.showAll();
            model.addAttribute("categoryList", categoryList);

            return "/product/admin_list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "물품 목록을 가져오는데에 문제가 있었습니다.");
            return "redirect:/";
        }
    }



    @PostMapping("/admin/product_list.do")
    public String productListSearch (
            HttpServletRequest req,
            HttpSession session
    ){
        String queryString = req.getQueryString();
        try{
            return "redirect:/product/admin/product_list.do?"+queryString;
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "물품 목록을 가져오는데에 문제가 있었습니다.");
            return "redirect:/";
        }
    }

}
/*
SELECT COUNT(*) FROM (SELECT product.* FROM product LEFT JOIN category_conn cc ON product.product_code
= cc.product_code WHERE cc.category_id in ('511') AND product.p_det in ('p1' ,'p0' ) GROUP
BY product.product_code) as db;
 */