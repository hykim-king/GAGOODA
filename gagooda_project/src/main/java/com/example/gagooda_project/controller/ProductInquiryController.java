package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.ProductInquiryServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/product_inquiry")
public class ProductInquiryController {
    ProductInquiryServiceImp productInquiryService;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ProductInquiryController(ProductInquiryServiceImp productInquiryService) {
        this.productInquiryService = productInquiryService;
    }

    @Data
    class AjaxStateHandler {
        private int state = 0; //0:실패 1:성공 (statusCode: 400(badRequest), 500(db통신 오류), 405(Method 오류))
    }

    @GetMapping("/{productCode}/list.do")
    public String list(Model model,
                       PagingDto paging,
                       @PathVariable(name = "productCode") String productCode,
                       @SessionAttribute(required = false) String msg,
                       HttpSession session,
                       @SessionAttribute(required = false) UserDto loginUser,
                       HttpServletRequest req
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        int list = 0;
        if (paging.getOrderField() == null) paging.setOrderField("p_inquiry_id");
//        List<ProductInquiryDto> plist = productInquiryService.showInquiries(productCode, paging);
        List<ProductInquiryDto> plist = productInquiryService.showAllInquiries(productCode);
        int count = productInquiryService.numPInquiryId(productCode, paging);
        try {
            log.info(productCode);
            model.addAttribute("count", count);
            model.addAttribute("plist", plist);
            model.addAttribute("paging", paging);
            list = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list > 0) {
            return "/product_inquiry/list";
        } else {
            return "/errorHandler";
        }

    }

    @GetMapping("/user_yes/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String msg,
            Model model,
            @PathVariable(required = true, name = "productCode") String productCode,
            HttpSession session
    ) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        int check = 0;
        List<CommonCodeDto> commonCodeList = productInquiryService.showCommonCode("pi");
        List<OptionProductDto> optionProductList = productInquiryService.showOptionProduct(productCode);
        System.out.println(commonCodeList);
        System.out.println(optionProductList);
        try {
            model.addAttribute("commonCodeList", commonCodeList);
            model.addAttribute("optionProductList", optionProductList);
            check = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (check > 0) {
            session.setAttribute("msg", "성공적으로 등록 됐습니다.");
            return "/product_inquiry/user_yes/register";
        } else {
            session.setAttribute("msg", "등록에 실패 했습니다.");
            return "/errorHandler";
        }
    }

    @PostMapping("/user_yes/register.do")
    public @ResponseBody AjaxStateHandler register(
            ProductInquiryDto productInquiry,
            Model model,
            @SessionAttribute UserDto loginUser,
            HttpSession session
    ) {
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        if (loginUser.getUserId() == productInquiry.getUserId()) {
            try {
                String user = Integer.toString(loginUser.getUserId());
                System.out.println(user);
                model.addAttribute("user", loginUser.getUserId());
                System.out.println(productInquiry.getProductCode());
                int register = productInquiryService.registerProductInquiry(productInquiry);
                ajaxStateHandler.setState(register);
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "글을 올리는데에 문제가 있었습니다.");
            }
            System.out.println(productInquiry);
            System.out.println(productInquiry.getProductCode());
        } else {
            session.setAttribute("msg", "작성자가 일치하지 않습니다.");
        }
        return ajaxStateHandler;
    }

    @DeleteMapping("/user_yes/delete.do")
    public @ResponseBody AjaxStateHandler delete(
            @SessionAttribute UserDto loginUser,
            @RequestParam(name = "pInquiryId") int pInquiryId,
            HttpSession session
    ) {
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        try {
            ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
            if (loginUser.getUserId() == productInquiry.getUserId()) {
                //로그인한 계정과 문의글의 작성자가 같을 경우
                int delete = productInquiryService.removeOne(productInquiry.getPInquiryId());
                ajaxStateHandler.setState(delete);
            } else {
                //로그인한 계정과 문의글의 작성자가 다를 경우
                session.setAttribute("msg", "삭제 할 권한이 없습니다.");
            }
        } catch (Exception e) {
            //Service 들을 사용하는 중 오류가 난 경우
            e.printStackTrace();
            session.setAttribute("msg", "삭제하는 중 오류가 있었습니다.");
        }
        return ajaxStateHandler;
    }

    @GetMapping("/user_yes/list.do")
    public String userMypageList(
            @SessionAttribute UserDto loginUser,
            Model model,
            PagingDto paging,
            @SessionAttribute(required = false) String msg,
            HttpSession session
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        int list = 0;
        try {
            if (paging.getOrderField() == null) paging.setOrderField("user_id");
            System.out.println(loginUser.getUserId());
            List<ProductInquiryDto> mypageList = productInquiryService.showInquiryByUser(loginUser.getUserId(), paging);
            int count = productInquiryService.numUserId(loginUser.getUserId());
            model.addAttribute("mypageList", mypageList);
            model.addAttribute("count", count);
            model.addAttribute("paging", paging);
            list = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list > 0) {
            return "/product_inquiry/user_yes/list";
        } else {
            return "/errorHandler";
        }

    }

    //    admin
    @GetMapping("/admin/list.do")
    public String adminList(
            Model model,
            @SessionAttribute UserDto loginUser,
            PagingDto paging,
            HttpServletRequest req,
            @SessionAttribute(required = false) String msg,
            HttpSession session
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        if (loginUser.getGDet().equals("g1")) {
            int check = 0;
            try {
                if (paging.getOrderField() == null) paging.setOrderField("p_inquiry_id");
                paging.setQueryString(req.getParameterMap());
                System.out.println(paging);
                List<ProductInquiryDto> adminList = productInquiryService.showProductInquiries(paging);
                model.addAttribute("adminList", adminList);
                model.addAttribute("paging", paging);
                check = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (check > 0) {
                return "/product_inquiry/admin/list";
            } else {
                return "/errorHandler";
            }
        } else {
            return "index";
        }
    }

    @GetMapping("/admin/{pInquiryId}/detail.do")
    public String adminDetail(
            @PathVariable int pInquiryId,
            Model model,
            @SessionAttribute(required = false) String msg,
            HttpSession session
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        int check = 0;
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        System.out.println(productInquiry);
        try {
            model.addAttribute("productInquiry", productInquiry);
            check = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (check > 0) {
            return "/product_inquiry/admin/detail";
        } else {
            return "/errorHandler";
        }

    }

    @GetMapping("/admin/detail.do")
    public String adminDetail(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String msg,
            Model model,
            HttpSession session
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "/product_inquiry/admin/detail";
    }

    @PostMapping("/admin/detail.do")
    public String adminDetail(
            @SessionAttribute UserDto loginUser,
            @RequestParam(name = "pInquiryId") int pInquiryId,
            String reply,
            HttpSession session
    ) {
        int modify = 0;
        System.out.println(pInquiryId);
        System.out.println(reply);
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        productInquiry.setReplyId(loginUser.getUserId());
        productInquiry.setReply(reply);
        System.out.println("***********************************" + productInquiry);
        try {
            modify = productInquiryService.modifyOne(productInquiry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (modify > 0) {
            session.setAttribute("msg", "성공적으로 수정 됐습니다.");
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            session.setAttribute("msg", "수정 중에 오류가 있었습니다.");
            return "redirect:/product_inquiry/admin/" + pInquiryId + "/detail.do";
        }
    }


    @PostMapping("/admin/list.do")
    public String adminUpdate(
            @SessionAttribute UserDto loginUser,
            @RequestParam(name = "pInquiryId") int pInquiryId,
            String reply,
            HttpSession session
    ) {
        int modify = 0;
        System.out.println(pInquiryId);
        System.out.println(reply);
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        productInquiry.setReplyId(loginUser.getUserId());
        productInquiry.setReply(reply);
        System.out.println("***********************************" + productInquiry);
        try {
            modify = productInquiryService.modifyOne(productInquiry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (modify > 0) {
            session.setAttribute("msg", "성공적으로 수정 됐습니다.");
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            session.setAttribute("msg", "수정 중에 오류가 있었습니다.");
            return "/product_inquiry/admin/list";
        }
    }

    @GetMapping("/admin/delete.do")
    public String admindelete(
            @SessionAttribute UserDto loginUser,
            @RequestParam(name = "pInquiryId") int pInquiryId,
            @SessionAttribute(required = false) String msg,
            HttpSession session,
            Model model
    ) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        int delete = 0;
        ProductInquiryDto productInquiry = productInquiryService.showDetail(pInquiryId);
        try {
            delete = productInquiryService.removeOne(productInquiry.getPInquiryId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (delete > 0) {
            session.setAttribute("msg", "성공적으로 삭제 했습니다.");
            return "redirect:/product_inquiry/admin/list.do";
        } else {
            session.setAttribute("msg", "삭제하는 중에 오류가 있었습니다.");
            return "redirect:/product_inquiry/admin/" + pInquiryId + "/detail.do";
        }
    }
}
