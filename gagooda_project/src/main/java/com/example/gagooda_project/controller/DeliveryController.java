package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.DeliveryDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.DeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {
    DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/admin/register.do")
    public String register(@SessionAttribute UserDto loginUser) {
        return "/delivery/register";
    }

    @PostMapping("/admin/register.do")
    public String register(DeliveryDto delivery,
                           @SessionAttribute UserDto loginUser) {
        int register = 0;
        try {
//            register= deliveryService.register(delivery);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(delivery);
        if (register>0) {
            return "redirect:index";
        } else {
            return "redirect:index";
        }
    }

}
