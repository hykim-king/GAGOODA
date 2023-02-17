package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.CategoryConnService;
import com.example.gagooda_project.service.ImageService;
import com.example.gagooda_project.service.OptionProductService;
import com.example.gagooda_project.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CategoryConnService categoryConnService;
    private ImageService imageService;
    private OptionProductService optionProductService;

    public ProductController(ProductService productService,
                             CategoryConnService categoryConnService,
                             ImageService imageService,
                             OptionProductService optionProductService) {
        this.productService = productService;
        this.categoryConnService = categoryConnService;
        this.imageService = imageService;
        this.optionProductService = optionProductService;
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
            @PathVariable int categoryId
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
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
    public String insert(
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
            return "/product/register";
        } else {
            session.setAttribute("msg", "관리자만 상품을 등록 할 수 있습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/admin/register.do")
    public String insert(
            @SessionAttribute UserDto loginUser,
            HttpSession session,
            ProductDto product,
            @RequestParam(name = "categoryId") List<Integer> categoryIdList,
            @RequestParam(name = "imageFile") List<MultipartFile> imageFileList,
            @RequestParam(name = "infoImageFile") List<MultipartFile> infoImageFileList
    ) {
        if (loginUser.getGDet().equals("g1")) {
            // imgCode, infoImgCode 설정
            product.setImgCode(product.getProductCode());
            product.setInfoImgCode(product.getProductCode() + "_INFO");
            product.setRegId(loginUser.getUserId());
            product.setModId(loginUser.getUserId());
            System.out.println(product.getInfoImgCode());

            // categoryConnList 생성 및 product 에 설정
            List<CategoryConnDto> categoryConnList = new ArrayList<>();
            for (int categoryId : categoryIdList) {
                CategoryConnDto categoryConn = new CategoryConnDto();
                categoryConn.setProductCode(product.getProductCode());
                categoryConn.setCategoryId(categoryId);
                categoryConnList.add(categoryConn);
            }
            product.setCategoryConnList(categoryConnList);
            int insert = 0;
            try {
                // product, categoryConn 등록
                insert = productService.register(product);
                for (int i = 0; i < imageFileList.size(); i++) {
                    insert += imageService.registerMultipartImage(imageFileList.get(i),
                            imgPath + "/product",
                            product.getImgCode(),
                            i + 1);
                }
                System.out.println("imageFileList 완료");
                for (int i = 0; i < imageFileList.size(); i++) {
                    insert += imageService.registerMultipartImage(infoImageFileList.get(i),
                            imgPath + "/product",
                            product.getInfoImgCode(),
                            i + 1);
                }
                System.out.println("insert:" + insert);
            } catch (Exception e) {
                e.printStackTrace();
                imageService.removeWithCode(product.getImgCode());
                imageService.removeWithCode(product.getInfoImgCode());
                optionProductService.removeWithProduct(product.getProductCode());
                categoryConnService.removeForProduct(product.getProductCode());
                productService.remove(product.getProductCode());
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

}
