package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.ProductInquiryDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ProductInquiryService;
import com.example.gagooda_project.service.ProductInquiryServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product_inquiry")
public class ProductInquiryController {
    ProductInquiryServiceImp productInquiryService;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ProductInquiryController(ProductInquiryServiceImp productInquiryService) {
        this.productInquiryService = productInquiryService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       @RequestParam(required = true, name = "productCode") String productCode,
                       @SessionAttribute(required = false) String succMsg,
                       HttpSession session
    ) {
        if (succMsg != null) {
            session.removeAttribute("succMsg");
            System.out.println(succMsg);
        }
        log.info(productCode);
        List<ProductInquiryDto> plist = productInquiryService.showInquiries(productCode);
        model.addAttribute("plist", plist);
        model.addAttribute("succMsg", succMsg);
        return "/product_inquiry/list";
    }

    @GetMapping("/register.do")
    public String register(
            @SessionAttribute UserDto loginUser
    ) {
        return "/product_inquiry/register";
    }

    @PostMapping("/register.do")
    public String register(ProductInquiryDto productInquiry,
                           Model model,
                           @SessionAttribute UserDto loginUser,
                           @RequestParam(required = true, name = "productCode") String productCode) {
        int register = 0;
        if (loginUser.getUserId() == productInquiry.getUserId()) {
            try {
                String user = Integer.toString(loginUser.getUserId());
                System.out.println(user);
                model.addAttribute("user", loginUser.getUserId());
//                System.out.println(productInquiry);
                System.out.println(productInquiry.getProductCode());
                register = productInquiryService.registerProductInquiry(productInquiry);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(productInquiry);
            System.out.println(productInquiry.getProductCode());
        }
        if (register > 0) {
            return "redirect:/product_inquiry/list.do?productCode="+productInquiry.getProductCode();
        } else {
            return "redirect:/product_inquiry/register.do";
        }
    }

    @GetMapping("delete.do")
    public String delete(@SessionAttribute UserDto loginUser,
                         @RequestParam(name="pInquiryId") int pInquiryId,
                         HttpSession session){
        int delete = 0;
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        if (loginUser.getUserId() == productInquiry.getUserId()) {
            try{
                delete = productInquiryService.removeOne(productInquiry.getPInquiryId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (delete > 0){
            session.setAttribute("succMsg", "삭제성공하였습니다.");
            return "redirect:/product_inquiry/list.do?productCode="+productInquiry.getProductCode();
        } else {

            return "redirect:/product_inquiry/list.do?productCode="+productInquiry.getProductCode();
        }
    }

//    admin
    @GetMapping("/admin/list.do")
    public String adminList(Model model,
                            @SessionAttribute UserDto loginUser){
        if(loginUser.getGDet().equals("g1")){
            List<ProductInquiryDto> adminList = productInquiryService.showProductInquiries();
            model.addAttribute("adminList",adminList);
            return "/product_inquiry/admin/list";
        } else {
            return "/index";
        }
    }
}
