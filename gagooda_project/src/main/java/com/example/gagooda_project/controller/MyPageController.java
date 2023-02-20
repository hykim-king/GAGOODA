package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.CommonCodeDto;
import com.example.gagooda_project.dto.ODetDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.mapper.CartMapper;
import com.example.gagooda_project.mapper.OrderDetailMapper;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.CartServiceImp;
import com.example.gagooda_project.service.MyPageService;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    CartService cartService;
    MyPageService myPageService;

    public MyPageController(MyPageService myPageService, CartService cartService) {
        this.myPageService = myPageService;
        this.cartService = cartService;
    }

    @GetMapping("/user_yes/main.do")
    public String myPageMain(@SessionAttribute UserDto loginUser,
                             Model model) {
        try {
            List<CartDto> cartList = myPageService.listByUserId(loginUser.getUserId());
            List<ODetDto> oDetList = myPageService.countByUserIdAndStatus(loginUser.getUserId());
            List<CommonCodeDto> myPageDetList = myPageService.showDetCodeList("o");
            model.addAttribute("cartList", cartList);
            model.addAttribute("oDetList", oDetList);
            model.addAttribute("myPageDetList",myPageDetList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mypage/main";
    }
}
