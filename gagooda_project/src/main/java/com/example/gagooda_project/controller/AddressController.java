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

    @GetMapping("/user_yes/mypage/register.do")
    public String register(@SessionAttribute(required = true) UserDto loginUser) {
        return "/address/register";
    }

    @PostMapping("/user_yes/mypage/register.do")
    public String register(AddressDto address,
                           @SessionAttribute(required = true) UserDto loginUser) {
        int register = 0;
        try {
            if(addressService.defaultAddress(address.getUserId())==null) {
                register = addressService.register(address);
            } else {
                if(addressService.defaultAddress(address.getUserId()).getAddressId()!=address.getAddressId() &&
                        address.isHome()==true) {
                    addressService.modifyDefault(address.getUserId());
                    register = addressService.register(address);
                } else {
                    register = addressService.register(address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(address);
        if (register > 0) {
            return "redirect:/address/user_yes/mypage/list.do";
        } else {
            return "redirect:/address/user_yes/mypage/register.do";
        }
    }

    @GetMapping("/user_yes/mypage/list.do")
    public String addressList(Model model,
                              @SessionAttribute(required = true) UserDto loginUser) {
        System.out.println(loginUser);
        List<AddressDto> alist = addressService.addressList(loginUser.getUserId());
        model.addAttribute("alist",alist);
        System.out.println(alist);
        return "address/list";
    }



    @GetMapping("/user_yes/mypage/modify.do")
    public String modifyOne(@SessionAttribute(required = true) UserDto loginUser,
                            Model model,
                            @RequestParam (name= "addressId") int addressId) {
        AddressDto address = addressService.selectOne(addressId);
        model.addAttribute("address",address);
        return "address/modify";
    }

    @GetMapping("/user_yes/mypage/dismissDefault.do")
    public String modifyDefault(@SessionAttribute(required = true) UserDto loginUser,
                                AddressDto address) {
        int modify=0;
        if(loginUser.getUserId() == address.getUserId()) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @PostMapping("/user_yes/mypage/modify.do")
    public String modifyOne(AddressDto address,
                            @SessionAttribute(required = true) UserDto loginUser) {
        int modify=0;
        if(loginUser.getUserId() == address.getUserId()){
            try {
                if(addressService.defaultAddress(address.getUserId())==null) {
                    modify = addressService.modifyOne(address);
                } else {
                    if(addressService.defaultAddress(address.getUserId()).getAddressId()!=address.getAddressId() &&
                            address.isHome()==true) {
                        addressService.modifyDefault(address.getUserId());
                        modify = addressService.modifyOne(address);
                    } else {
                        modify = addressService.modifyOne(address);
                    }
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(modify>0) {
            return "redirect:/address/user_yes/mypage/list.do";
        } else {
            return "redirect:/address/user_yes/mypage/modify.do?addressId="+address.getAddressId();
        }
    }

    @GetMapping("/user_yes/mypage/delete.do")
    public String removeOne(@RequestParam(name = "address_id") int addressId,
                            @SessionAttribute(required = true) UserDto loginUser) {
        int remove = 0;
        AddressDto address = addressService.selectOne(addressId);
        if (loginUser.getUserId() == address.getUserId()) {
            try {
                remove = addressService.removeOne(addressId);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if(remove>0) {
            return "redirect:list.do";
        } else {
            return "redirect:modify.do?addressId="+address.getAddressId();
        }

    }
}
