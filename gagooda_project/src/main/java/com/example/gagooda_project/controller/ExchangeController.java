package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.*;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    ExchangeService exchangeService;
    OrderService orderService;
    ImageServiceImp imageServiceImp;
    AddressServiceImp addressServiceImp;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")
    private String imgPath;

    public ExchangeController(ExchangeService exchangeService,
                              OrderService orderService,
                              ImageServiceImp imageServiceImp,
                              AddressServiceImp addressServiceImp) {
        this.exchangeService = exchangeService;
        this.orderService = orderService;
        this.imageServiceImp = imageServiceImp;
        this.addressServiceImp = addressServiceImp;
    }



    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       @SessionAttribute(required = false) String msg,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       @RequestParam(name = "startDate", required = false) String startDate,
                       @RequestParam(name = "endDate", required = false) String endDate,
                       @RequestParam(name = "detCode", required = false) String detCode,
                       PagingDto paging,
                       HttpServletRequest req,
                       HttpSession session,
                       Model model) {
        log.info("req.getParameterMap:"+req.getParameterMap());
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try {
            paging.setQueryString(req.getParameterMap());
            List<ExchangeDto> exchangeList = exchangeService.showUserExchangeList(loginUser.getUserId(), period, startDate, endDate, detCode, paging);
            int userExchangeCount = exchangeService.showCountByUser(loginUser.getUserId(), period, startDate, endDate, detCode);
            model.addAttribute("exchangeList", exchangeList);
            model.addAttribute("userExchangeCount", userExchangeCount);
            model.addAttribute("paging", paging);
            return "exchange/user/list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }

    @GetMapping("user_yes/mypage/{exchangeId}/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @SessionAttribute(required = false) String msg,
                         @PathVariable int exchangeId,
                         HttpSession session,
                         Model model) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        ExchangeDto exchange = null;
        try {
            exchange = exchangeService.selectOne(exchangeId);
            if (loginUser.getUserId() == exchange.getUserId()) {
                List<CommonCodeDto> exDetList = exchangeService.showDetCodeList("ex");
                List<CommonCodeDto> rfrDetList = exchangeService.showDetCodeList("rfr");
                OrderDto order = orderService.selectOne(exchange.getOrderId());
                model.addAttribute("exchange", exchange);
                model.addAttribute("exDetList", exDetList);
                model.addAttribute("rfrDetList", rfrDetList);
                model.addAttribute("order", order);
                return "exchange/user/detail";
            }else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }

    @PostMapping("user_yes/mypage/{exchangeId}/modify.do")
    public String modify(@PathVariable int exchangeId,
                         @SessionAttribute UserDto loginUser,
                         HttpSession session) {
        int modify = 0;
        ExchangeDto upExchange = exchangeService.selectOne(exchangeId);
        if (loginUser.getUserId() == upExchange.getUserId()) {
            try {
                modify += exchangeService.modifyOne(upExchange, "user");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (modify > 0) {
            session.setAttribute("msg", "교환 요청이 성공적으로 취소되었습니다.");
            return "redirect:/exchange/user_yes/mypage/list.do";
        } else {
            session.setAttribute("msg", "정보가 일치하지 않습니다.");
            return "redirect:/exchange/user_yes/mypage/"+exchangeId+"/detail.do";
        }
    }

    @GetMapping("admin/list.do")
    public String adminList(@SessionAttribute UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
                            PagingDto paging,
                            HttpServletRequest req,
                            @RequestParam(name = "exDet", required = false, defaultValue = "") String exDet,
                            @RequestParam(name = "searchDiv", required = false, defaultValue = "") String searchDiv,
                            @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
                            @RequestParam(name = "dateType", required = false, defaultValue = "") String dateType,
                            @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                            @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate,
                            Model model){
        log.info("req.getParameterMap:"+req.getParameterMap());
        log.info("requestUri:" +req.getRequestURI());
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try{
            if (loginUser.getGDet().equals("g1")){
                Map<String, Object> searchFilter = new HashMap<>();
                paging.setQueryString(req.getParameterMap());
                searchFilter.put("exDet",exDet); searchFilter.put("searchDiv",searchDiv); searchFilter.put("searchWord", searchWord);
                searchFilter.put("dateType", dateType); searchFilter.put("startDate", startDate); searchFilter.put("endDate", endDate);
                searchFilter.put("paging", paging);
                List<CommonCodeDto> exCodeList = exchangeService.showDetCodeList("ex");
                List<ExchangeDto> exchangeList = exchangeService.showExchangeList(searchFilter);
                int exchangeCount = exchangeService.countPageAll(searchFilter);
                int allExCnt = exchangeService.countAll();
                model.addAttribute("exchangeList", exchangeList);
                model.addAttribute("exCodeList", exCodeList);
                model.addAttribute("paging",paging);
                model.addAttribute("exchangeCount", exchangeCount);
                model.addAttribute("allCount",allExCnt);
                return "exchange/admin/list";
            }
            else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }

    @GetMapping("admin/{exchangeId}/detail.do")
    public String adminDetail(@PathVariable int exchangeId,
                              HttpSession session,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser,
                              @SessionAttribute(required = false) String msg,
                              Model model){
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try{
            if(loginUser.getGDet().equals("g1")){
                ExchangeDto exchange = exchangeService.selectOne(exchangeId);
                OrderDetailDto orderDetail = exchangeService.selectOrderDetailById(exchange.getOrderDetailId());
                OptionProductDto optionProduct = exchangeService.selectOptionProductByOptionCode(orderDetail.getOptionCode());
                OrderDto order = orderService.selectOne(exchange.getOrderId());
                session.setAttribute("prevUri",req.getRequestURI());
                List<CommonCodeDto> allExList = exchangeService.showDetCodeList("ex");
                model.addAttribute("exchange", exchange);
                model.addAttribute("orderDetail", orderDetail);
                model.addAttribute("optionProduct", optionProduct);
                model.addAttribute("order",order);
                model.addAttribute("exCodeList", allExList);
                return "exchange/admin/detail";
            }else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        }catch (Exception e){
            log.info(e.getMessage());
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }

    @PostMapping("admin/{exchangeId}/modify.do")
    public String adminModify(@PathVariable int exchangeId,
                              ExchangeDto exchange,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser){
        int modify = 0;
        if(loginUser.getGDet().equals("g1") && exchangeId == exchange.getExchangeId()){
            try{
                modify += exchangeService.modifyOne(exchange, "admin");
                if(modify > 0){
                    return "redirect:/exchange/admin/"+exchangeId+"/detail.do";
                }
            }catch (Exception e){
                e.printStackTrace();
                return "redirect:"+req.getRequestURI();
            }
        }
        return "redirect:"+req.getRequestURI();
    }

    @PostMapping("admin/{exchangeId}/stockModify.do")
    @ResponseBody
    public int stockModify(@PathVariable int exchangeId,
                                         OptionProductDto optionProduct,
                                         @SessionAttribute UserDto loginUser){
        int modify = 0;
        ExchangeDto exchange = null;
        OrderDetailDto orderDetail = null;
        OptionProductDto checkOptPro = null;
        if(loginUser.getGDet().equals("g1")){
            try{
                exchange = exchangeService.selectOne(exchangeId);
                orderDetail = exchangeService.selectOrderDetailById(exchange.getOrderDetailId());
                checkOptPro = exchangeService.selectOptionProductByOptionCode(orderDetail.getOptionCode());
                if(checkOptPro.getOptionCode().equals(optionProduct.getOptionCode())){
                    int stock = checkOptPro.getStock();
                    int subCount = exchange.getCnt();
                    int modifiedStock = stock - subCount;
                    if (modifiedStock > 0){
                        modify = exchangeService.modifyOptionProductStock(modifiedStock, orderDetail.getOptionCode());
                        return modify;
                    }else if(modifiedStock < 0){
                        modify = -1;
                        return modify;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return modify;
    }
}
