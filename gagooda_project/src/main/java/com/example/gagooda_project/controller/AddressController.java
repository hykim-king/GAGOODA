package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {
    AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/register.do")
    public String register(@SessionAttribute(required = true) UserDto loginUser) {
        return "/address/register";
    }

    @PostMapping("/register.do")
    public String register(AddressDto address,
                           @SessionAttribute(required = true) UserDto loginUser) {
        int register = 0;
        try {
            register = addressService.register(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(address);
        if (register > 0) {
            return "redirect:/address/list.do";
        } else {
            return "redirect:/address/register.do";
        }
    }

    @GetMapping("/list.do")
    public String addressList(Model model,
                              @SessionAttribute(required = true) UserDto loginUser) {
        System.out.println(loginUser);
        List<AddressDto> alist = addressService.addressList(loginUser.getUserId());
        model.addAttribute("alist",alist);
        System.out.println(alist);
        return "/address/list";
    }

    @GetMapping("/{addressId}/modify.do")
    public String modifyOne(@PathVariable int addressId,
                            @SessionAttribute(required = true) UserDto loginUser,
                            Model model) {
        AddressDto address = addressService.selectOne(addressId);
        model.addAttribute("address",address);
        return "/address/modify";
    }

    @PostMapping("/modify.do")
    public String modifyOne(AddressDto address,
                            @SessionAttribute(required = true) UserDto loginUser) {
        int modify=0;
        if(loginUser.getUserId() == address.getUserId()){
            try {
                modify = addressService.modifyOne(address);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(modify>0) {
            return "redirect:/address/list";
        } else {
            return "redirect:/address/modify.do";
        }
    }

}
