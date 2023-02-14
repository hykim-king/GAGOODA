package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address/register.do")
    public String register(@SessionAttribute(name = "loginUser") UserDto loginUser) {
        return "/address/list.do";
    }

    @PostMapping("/address/register.do")
    public String register(AddressDto address,
                           @SessionAttribute UserDto loginUser) {
        int register = 0;
        if (loginUser.getUserId() == address.getUserId()) {
            try {
                register = addressService.register(address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(address);
        if (register > 0) {
            return "redirect:/address/list.do";
        } else {
            return "redirect:/address/register.do";
        }
    }
}
