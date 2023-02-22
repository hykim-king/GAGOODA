package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.DeliveryService;
import com.example.gagooda_project.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

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
                         @PathVariable String orderId,
                         Model model) {
        DeliveryDto delivery = deliveryService.selectByOrderId(orderId);
        if(loginUser.getUserId() == delivery.getUserId()) {
            try{
                List<CommonCodeDto> allDList = deliveryService.showDetCodeList("d");
                model.addAttribute("delivery",delivery);
                model.addAttribute("dCodeList",allDList);
                return "delivery/detail";
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return "index";
    }

}
