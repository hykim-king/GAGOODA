package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.AddressDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.AddressService;
import jakarta.servlet.http.HttpSession;
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
    public String register(@SessionAttribute(required = true) UserDto loginUser,
                           @SessionAttribute(required = false) String msg,
                           HttpSession session,
                           Model model) {
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try{
            return "/address/register";
        } catch(Exception e) {
            session.setAttribute("msg","등록 중 오류가 발생하셨습니다.");
            return "redirect:/user_yes/mypage/register.do";
        }
    }

    @PostMapping("/user_yes/mypage/register.do")
    public String register(AddressDto address,
                           @SessionAttribute(required = true) UserDto loginUser,
                           HttpSession session) {
        int register = 0;
        try {
            if(addressService.defaultAddress(address.getUserId())==null) {
                register = addressService.register(address);
            } else {
                if(addressService.defaultAddress(address.getUserId()).getAddressId()!=address.getAddressId() && address.isHome()==true) {
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
            session.setAttribute("msg", "등록 중 오류가 발생했습니다.");
            return "redirect:/address/user_yes/mypage/register.do";
        }
    }

    @GetMapping("/user_yes/mypage/list.do")
    public String addressList(Model model,
                              @SessionAttribute(required = true) UserDto loginUser,
                              @SessionAttribute(required = false) String msg,
                              HttpSession session) {
//        System.out.println(loginUser);
        if(msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try{
            List<AddressDto> alist = addressService.addressList(loginUser.getUserId());
            model.addAttribute("alist",alist);
            return "address/list";
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 찾을 수 없습니다.");
            return "redirect:/";
        }
    }



    @GetMapping("/user_yes/mypage/modify.do")
    public String modifyOne(@SessionAttribute(required = true) UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
                            Model model,
                            @RequestParam (name= "addressId") int addressId) {
        if (msg!=null) {
            model.addAttribute("msg",msg);
            session.removeAttribute("msg");
        }
        try {
            AddressDto address = addressService.selectOne(addressId);
            model.addAttribute("address",address);
            return "address/modify";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg","수정 폼을 불러올 수 없습니다.");
            return "redirect:/";
        }
    }
    
    @PostMapping("/user_yes/mypage/modify.do")
    public String modifyOne(AddressDto address,
                            @SessionAttribute(required = true) UserDto loginUser,
                            HttpSession session) {
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
            session.setAttribute("msg","수정 중 오류가 발생했습니다.");
            return "redirect:/address/user_yes/mypage/modify.do?addressId="+address.getAddressId();
        }
    }

    @GetMapping("/user_yes/mypage/delete.do")
    public String removeOne(@RequestParam(name = "address_id") int addressId,
                            @SessionAttribute(required = true) UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
                            Model model) {
        int remove = 0;
        if(msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
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
            session.setAttribute("msg","상품 삭제에 실패했습니다.");
            return "redirect:modify.do?addressId="+address.getAddressId();
        }
    }
}
