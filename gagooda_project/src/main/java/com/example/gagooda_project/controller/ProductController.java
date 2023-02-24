package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.*;
import com.sun.jdi.event.ExceptionEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CategoryService categoryService;
    private final CommonCodeService commonCodeService;
    private final ProductInquiryService productInquiryService;
    private final CategoryConnService categoryConnService;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             CommonCodeService commonCodeService,
                             ProductInquiryService productInquiryService,
                             CategoryConnService categoryConnService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.commonCodeService = commonCodeService;
        this.productInquiryService = productInquiryService;
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
            @PathVariable String categoryId,
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

        Map<String, Object> map = new HashMap<>();
        List<String> categoryIdList = new ArrayList<>();
        categoryIdList.add(categoryId);
        map.put("categoryIdList", categoryIdList);
        try {
            List<ProductDto> productList = productService.pagingProduct(paging, map);
            model.addAttribute("paging", paging);
            model.addAttribute("productList", productList);

            model.addAttribute("realCategoryId", categoryId);
            if (categoryId.length() == 3) {
                CategoryDto category = categoryService.selectOne(categoryId);
                categoryId = category.getParentId();
            }

            CategoryDto category = categoryService.selectOne(categoryId);
            model.addAttribute("category", category);

            List<CategoryDto> childList = categoryService.showChildCategories(categoryId);
            model.addAttribute("childList", childList);

            if (category.getParentId() != null) {
                CategoryDto parentCategory = categoryService.selectOne(category.getParentId());
                model.addAttribute("parentCategory", parentCategory);
            }
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
            @SessionAttribute(required = false) UserDto loginUser,
            PagingDto paging
    ) {
        System.out.println("msg " + msg);
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try {
            ProductDto product = productService.selectOne(productCode);
            List<ProductInquiryDto> plist = productInquiryService.showInquiries(productCode, paging);
//            List<ProductInquiryDto> plist = productInquiryService.showAllInquiries(productCode);
            List<CommonCodeDto> commonCodeList = commonCodeService.showDets("pi");
            model.addAttribute("paging", paging);
            model.addAttribute("product", product);
            model.addAttribute("plist", plist);
            model.addAttribute("commonCodeList", commonCodeList);
            return "/product/detail";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 찾을 수 없습니다.");
            return "redirect:/";
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
                attributesSet(model);
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
            int insert;
            try {
                // product, categoryConn 등록
                insert = productService.register(product, imageFileList, infoImageFileList,
                        loginUser, categoryIdList, imgPath);
                System.out.println("insert:" + insert);
            } catch (Exception | Error e) {
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
            @RequestParam(required = false, name = "categoryId") List<String> categoryIdList,
            @RequestParam(required = false, name = "catDet") List<String> catDetList,
            @RequestParam(required = false, defaultValue = "") String searchWord,
            Model model,
            PagingDto paging,
            HttpServletRequest req,
            HttpSession session,
            @SessionAttribute(required = false) String msg
    ){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
            System.out.println(msg);
        }
        paging.setRows(20);
        if (paging.getOrderField() == null) paging.setOrderField("mod_date");
        paging.setQueryString(req.getParameterMap());
        Map<String, Object> map = new HashMap<>();
        if (categoryIdList != null) {
            map.put("categoryIdList", categoryIdList);
            model.addAttribute("categoryIdList", categoryIdList);
        }
        if (catDetList != null) {
            map.put("catDetList", catDetList);
            model.addAttribute("catDetList", catDetList);
        }
        if (searchWord != null && !searchWord.isBlank()) {
            map.put("searchWord", "'%"+searchWord+"%'");
            model.addAttribute("searchWord", searchWord);
        }
        try {
            List<ProductDto> productList = productService.pagingProduct(paging, map);
            model.addAttribute("productList", productList);
            model.addAttribute("paging", paging);

            Map<String, String> detDict = commonCodeService.showNames("p");
            model.addAttribute("detDict", detDict);

            Map<String, String> levelOne = categoryService.categoryDict(1);
            Map<String, String> levelTwo = categoryService.categoryDict(2);
            Map<String, String> levelThree = categoryService.categoryDict(3);
            model.addAttribute("levelOne", levelOne);
            model.addAttribute("levelTwo", levelTwo);
            model.addAttribute("levelThree", levelThree);

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

    @GetMapping("/admin/{productCode}/modify.do")
    public String modify(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String msg,
            @PathVariable String productCode,
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
                ProductDto product = productService.selectOne(productCode);
                model.addAttribute("product", product);
                List<CategoryConnDto> categoryConnList = categoryConnService.showForProduct(productCode);
                model.addAttribute("categoryConnList", categoryConnList);
                attributesSet(model);
                return "/product/modify";
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

    @PostMapping("/admin/{productCode}/modify.do")
    public String modify(
            @SessionAttribute UserDto loginUser,
            HttpSession session,
            @PathVariable String productCode,
            ProductDto product,
            @RequestParam(required = false, name = "category") HashSet<String> categoryIdList,
            @RequestParam(required = false, name = "imageFile") List<MultipartFile> imageFileList,
            @RequestParam(required = false, name = "infoImageFile") List<MultipartFile> infoImageFileList,
            @RequestParam(required = false, name = "imgToDelete") List<String> imgToDelete,
            @RequestParam(required = false, name = "optionToDelete") List<String> optionToDeleteList
    ) {
        if (loginUser.getGDet().equals("g1")) {
            try {
                int modify = productService.modifyOne(product, imageFileList, infoImageFileList,
                        categoryIdList, imgToDelete, loginUser, imgPath, optionToDeleteList);
                log.info("modify 성공: "+modify);
                session.setAttribute("msg", "성공적으로 수정 되었습니다.");
                return "redirect:/product/admin/product_list.do";
            } catch (Exception | Error e) {
                e.printStackTrace();
                session.setAttribute("msg", "수정 중 오류가 있었습니다.");
                return "redirect:/product/admin/"+productCode+"/modify.do";
            }
        } else {
            session.setAttribute("msg", "접근 권한이 없습니다.");
            return "redirect:/";
        }
    }

    private void attributesSet(Model model) {
        List<CategoryDto> levelOne = categoryService.showCategoriesAt(1);
        List<CategoryDto> levelTwo = categoryService.showCategoriesAt(2);
        List<CategoryDto> levelThree = categoryService.showCategoriesAt(3);
        List<CommonCodeDto> productDet = commonCodeService.showDets("p");
        model.addAttribute("levelOne", levelOne);
        model.addAttribute("levelTwo", levelTwo);
        model.addAttribute("levelThree", levelThree);
        model.addAttribute("productDet", productDet);
    }
}