package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.ProductInquiryDto;
import com.example.gagooda_project.service.ProductInquiryServiceImp;
import jakarta.servlet.http.HttpServletRequest;
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
    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ProductInquiryController(ProductInquiryServiceImp productInquiryService){
        this.productInquiryService = productInquiryService;
    }

    @GetMapping("/list.do")
    public String productInquirylist(Model model,
                                     @RequestParam(required = true, name = "productCode") String productCode){
        log.info(productCode);
        List<ProductInquiryDto> plist = productInquiryService.showInquiries(productCode);
        model.addAttribute("plist", plist);
        return "/product_inquiry/list";
    }

    @GetMapping("/register.do")
    public void productInquiryRegister(){
    }

    @PostMapping("/register.do")
    public String productInquiryRegister(ProductInquiryDto productInquiryDto){
        int register = 0;
        try {
            register = productInquiryService.registerProductInquiry(productInquiryDto);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (register > 0){
            return "redirect:/product_inquiry/list.do?p_inquiry_id="+productInquiryDto.getPInquiryId();
        }
        return "redirect:/product_inquiry/register";
    }




}
