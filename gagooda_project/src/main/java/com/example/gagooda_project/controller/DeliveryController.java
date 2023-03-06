package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.DeliveryService;
import com.example.gagooda_project.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    DeliveryService deliveryService;
    OrderService orderService;

    public DeliveryController(DeliveryService deliveryService) {

        this.deliveryService = deliveryService;
        this.orderService = orderService;
    }
    @GetMapping("user_yes/mypage/{orderId}/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @SessionAttribute(required = false) String msg,
                         @PathVariable String orderId,
                         HttpSession session,
                         Model model) {
        DeliveryDto delivery = deliveryService.selectByOrderId(orderId);
        if (msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        if(loginUser.getUserId() == delivery.getUserId()) {
            try{
                List<CommonCodeDto> allDList = deliveryService.showDetCodeList("d");
                model.addAttribute("delivery",delivery);
                model.addAttribute("dCodeList",allDList);
                return "delivery/detail";
            } catch(Exception e) {
                session.setAttribute("msg","데이터를 가져오지 못 했습니다.");
                e.printStackTrace();
            }
        }
        return "index";
    }
    @GetMapping("admin/list.do")
    public String showDeliveryList(@SessionAttribute UserDto loginUser,
                                   @SessionAttribute(required = false) String msg,
                                   HttpSession session,
                                   PagingDto paging,
                                   HttpServletRequest req,
                                   @RequestParam(name = "dDet", required = false, defaultValue = "") String dDet,
                                   @RequestParam(name = "searchDiv", required = false, defaultValue = "") String searchDiv,
                                   @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
                                   @RequestParam(name = "dateType", required = false, defaultValue = "") String dateType,
                                   @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                                   @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate,
                                   Model model) {
        if(msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try {
            if(loginUser.getGDet().equals("g1")) {
                Map<String, Object> searchFilter = new HashMap<>();
                paging.setQueryString(req.getParameterMap());
                System.out.println("@@@@@@@@@@@@@@@"+paging);
                searchFilter.put("dDet",dDet); searchFilter.put("searchDiv",searchDiv); searchFilter.put("searchWord",searchWord);
                searchFilter.put("dateType",dateType); searchFilter.put("startDate",startDate); searchFilter.put("endDate",endDate);
                searchFilter.put("paging", paging);
                List<CommonCodeDto> dCodeList = deliveryService.showDetCodeList("d");
                List<DeliveryDto> deliveryList = deliveryService.showDeliveryList(searchFilter);
                int deliveryCount = deliveryService.countListAll(searchFilter);
                int allDCnt = deliveryService.countAll();
                model.addAttribute("allDCnt",allDCnt);
                model.addAttribute("deliveryList",deliveryList);
                model.addAttribute("dCodeList",dCodeList);
                System.out.println("paging for delivery: "+paging);
                model.addAttribute("paging",paging);
                model.addAttribute("deliveryCount",deliveryCount);
                return "delivery/list";
            } else {
                session.setAttribute("msg","배송 정보를 불러오는데에 실패했습니다.");
                return "/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "delivery/list";
    }

    @GetMapping("admin/{orderId}/modify.do")
    public String modifyOne(@SessionAttribute UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
                            Model model,
                            @PathVariable String orderId){
        DeliveryDto delivery = deliveryService.selectByOrderId(orderId);
        if(msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        if(loginUser.getGDet().equals("g1")) {
            try {
                model.addAttribute("delivery",delivery);
                return "delivery/modify";
            } catch(Exception e) {
                e.printStackTrace();
                session.setAttribute("msg","데이터를 가져오는데 실패하였습니다.");
                return "redirect:/";
            }
        } else {
            session.setAttribute("msg","관리자만 배송정보를 수정할 수 있습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("admin/modify.do")
    public String modifyOne(DeliveryDto delivery,
                            @SessionAttribute UserDto loginUser,
                            HttpSession session) {
        int modify = 0;
        try{
            if (loginUser.getGDet().equals("g1")) {
                modify = deliveryService.modifyOne(delivery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(modify>0) {
            return "redirect:/delivery/admin/list.do";
        } else {
            session.setAttribute("msg","배송 정보 수정 중 오류가 발생했습니다.");
            return "redirect:/delivery/admin"+delivery.getOrderId()+"/modify.do";
        }
    }

    @GetMapping("/admin/{orderId}/delete.do")
    public String removeOne(@PathVariable String orderId,
                            @SessionAttribute UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
                            Model model) {
        int remove = 0;
        if(msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try{
//            System.out.println("여기까진 오냐");
            if(loginUser.getGDet().equals("g1")) {
                System.out.println("여긴 왜 안와");
                remove = deliveryService.removeOne(orderId);
            } else {
                session.setAttribute("msg","관리자만 삭제 할 수 있습니다.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(remove>0) {
            return "redirect:/delivery/admin/list.do";
        } else {
            session.setAttribute("msg","배송 정보를 삭제하는데 실패했습니다.");
            return "redirect:/delivery/admin/"+orderId+"/modify.do";
        }
    }
}
