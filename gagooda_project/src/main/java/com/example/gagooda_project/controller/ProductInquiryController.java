package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.ProductInquiryServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
                       PagingDto paging,
                       @RequestParam(required = true, name = "productCode") String productCode,
                       @SessionAttribute(required = false) String succMsg,
                       HttpSession session,
                       HttpServletRequest req
    ) {
        int list = 0;
        if(paging.getOrderField()==null)paging.setOrderField("p_inquiry_id");
        paging.setQueryString(req.getParameterMap());
//        System.out.println(paging);
        List<ProductInquiryDto> plist = productInquiryService.showInquiries(productCode, paging);
        System.out.println(plist);
        System.out.println(paging);
        int count = productInquiryService.numPInquiryId(productCode, paging);
        try{
            if (succMsg != null) {
                session.removeAttribute("succMsg");
                System.out.println(succMsg);
            }
            log.info(productCode);
            model.addAttribute("count", count);
            model.addAttribute("plist", plist);
            model.addAttribute("succMsg", succMsg);
            model.addAttribute("paging",paging);
            list = 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (list > 0){
            return "/product_inquiry/list";
        } else {
            return "/errorHandler";
        }

    }

    @GetMapping("/user_yes/{productCode}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           Model model,
                           @PathVariable(required = true, name = "productCode") String productCode
    ) {
        int check = 0;
        List<CommonCodeDto> commonCodeList = productInquiryService.showCommonCode("pi");
        List<OptionProductDto> optionProductList = productInquiryService.showOptionProduct(productCode);
        System.out.println(commonCodeList);
        System.out.println(optionProductList);
        try {
            model.addAttribute("commonCodeList", commonCodeList);
            model.addAttribute("optionProductList", optionProductList);
            check = 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (check > 0){
            return "/product_inquiry/user_yes/register";
        } else {
            return "/errorHandler";
        }
    }

    @PostMapping("/user_yes/{productCode}/register.do")
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
            return "redirect:/product_inquiry/user_yes/register.do";
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
                            @SessionAttribute UserDto loginUser,
                            PagingDto paging,
                            HttpServletRequest req){
        if(loginUser.getGDet().equals("g1")){
            int check = 0;
            try {
                if(paging.getOrderField()==null)paging.setOrderField("p_inquiry_id");
                paging.setQueryString(req.getParameterMap());
                System.out.println(paging);
                List<ProductInquiryDto> adminList = productInquiryService.showProductInquiries(paging);
                model.addAttribute("adminList",adminList);
                model.addAttribute("paging",paging);
                check = 1;
            } catch (Exception e){
                e.printStackTrace();
            }
            if (check > 0){
                return "/product_inquiry/admin/list";
            } else {
                return "/errorHandler";
            }
        } else {
            return "index";
        }
    }

    @GetMapping("/admin/{pInquiryId}/detail.do")
    public String adminDetail(@PathVariable int pInquiryId,
                              Model model){
        int check =0;
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        System.out.println(productInquiry);
        try {
            model.addAttribute("productInquiry",productInquiry);
            check = 1;
        } catch (Exception e){
            e.printStackTrace();
        }
        if (check > 0){
            return "/product_inquiry/admin/detail";
        } else {
            return "/errorHandler";
        }

    }

    @GetMapping("/admin/detail.do")
    public String adminDetail(@SessionAttribute UserDto loginUser){
        return "/product_inquiry/admin/detail";
    }

    @PostMapping("/admin/detail.do")
    public String adminDetail(@SessionAttribute UserDto loginUser,
                              @RequestParam(name="pInquiryId") int pInquiryId,
                              String reply){
        int modify = 0;
        System.out.println(pInquiryId);
        System.out.println(reply);
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        productInquiry.setReplyId(loginUser.getUserId());
        productInquiry.setReply(reply);
        System.out.println("***********************************"+productInquiry);
        try {
            modify =productInquiryService.modifyOne(productInquiry);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (modify > 0){
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            return "redirect:/product_inquiry/admin/"+pInquiryId+"/detail.do";
        }
    }


    @PostMapping ("/admin/list.do")
    public String adminUpdate(@SessionAttribute UserDto loginUser,
                              @RequestParam(name="pInquiryId") int pInquiryId,
                              String reply
    ){
        int modify = 0;
        System.out.println(pInquiryId);
        System.out.println(reply);
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        productInquiry.setReplyId(loginUser.getUserId());
        productInquiry.setReply(reply);
        System.out.println("***********************************"+productInquiry);
        try {
            modify =productInquiryService.modifyOne(productInquiry);
        } catch (Exception e){
            e.printStackTrace();
        }
        if (modify > 0){
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            return "/product_inquiry/admin/list";
        }
    }

    @GetMapping("/admin/delete.do")
    public String admindelete(@SessionAttribute UserDto loginUser,
                         @RequestParam(name="pInquiryId") int pInquiryId
                         ){
        int delete = 0;
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        try{
            delete = productInquiryService.removeOne(productInquiry.getPInquiryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (delete > 0){
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            return "redirect:/product_inquiry/admin/"+pInquiryId+"/detail.do";
        }
    }
}
