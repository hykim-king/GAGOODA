package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.RefundServiceImp;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;

    public RefundController(RefundServiceImp refundServiceImp) {
        this.refundServiceImp = refundServiceImp;
    }

    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       Model model){
        List<RefundDto> refundList = refundServiceImp.showUserRefundList(loginUser.getUserId(), period);
        model.addAttribute("refundList", refundList);
        return "refund/user/list";
    }

    @GetMapping("user_yes/mypage/register.do")
    public String register(@SessionAttribute(required = true) UserDto loginUser,
                            @RequestParam(name = "orderId") String orderId,
                            Model model){

        OrderDto order = refundServiceImp.selectOrder(orderId);
        List<OrderDetailDto> orderDetailList = refundServiceImp.showOrderDetailListByOrderId(orderId);
        int orderSumPrice = 0;
        for (OrderDetailDto orderDetailDto : orderDetailList) {
            orderSumPrice += orderDetailDto.getPrice();
        }
        List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
        AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());

        model.addAttribute("order", order);
        model.addAttribute("orderAddress", orderAddress);
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("orderSumPrice", orderSumPrice);
        model.addAttribute("addressList", addressList);
        return "refund/user/register";
    }
//    @PostMapping("/register.do")
//    public String register(int t){
//
//    }

}
