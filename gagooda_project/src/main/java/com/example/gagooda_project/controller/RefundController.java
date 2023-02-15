package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.RefundServiceImp;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mypage/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;

    public RefundController(RefundServiceImp refundServiceImp) {
        this.refundServiceImp = refundServiceImp;
    }

    @GetMapping("/list.do")
    public String list(/*@SessionAttribute(required = true) UserDto loginUser,*/
                       @RequestParam(name= "userId") int userId,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       Model model){
        List<RefundDto> refundList = refundServiceImp.showUserRefundList(/*loginUser.getUserId()*/userId, period);
        model.addAttribute("refundList", refundList);
        return "refund/user/list";
    }

    @GetMapping("/register.do")
    public String register(@SessionAttribute(required = true) UserDto loginUser,
                            @RequestParam(name = "orderId") String orderId,
                            Model model){
        OrderDto order = refundServiceImp.selectOrder(orderId);
        List<OrderDetailDto> orderDetailList = refundServiceImp.showOrderDetailListByOrderId(orderId);
        List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
        AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());
        model.addAttribute("order", order);
        model.addAttribute("orderAddress", orderAddress);
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("addressList", addressList);
        return "refund/user/register";
    }
//    @PostMapping("/register.do")
//    public String register(int t){
//
//    }

}
