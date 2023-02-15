package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/list.do")
    public String list(@RequestParam(name = "optionCode", required = false) String optionCode,
                       Model model,
                       @SessionAttribute UserDto loginUser) throws Exception {
        CartDto cart = cartService.selectOne(loginUser.getUserId(), optionCode);
        List<CartDto> cartList = cartService.cartList(loginUser.getUserId());
        int cnt = 0;
        cnt += cartService.countCartItems(loginUser.getUserId());
        model.addAttribute("cart", cart);
        model.addAttribute("cartList", cartList);
        return "/cart/list";
    }

    @GetMapping("/deleteOne.do")
    public String deleteOne(CartDto cart,
                            @SessionAttribute UserDto loginUser) throws Exception {
        int delete = 0;
        try {
            delete = cartService.removeOne(cart.getCartId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (delete > 0) {
            return "redirect:/cart/list.do";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/deleteAll.do")
    public String deleteAll(@SessionAttribute UserDto loginUser) throws Exception {
        int delete = 0;
        try {
            delete = cartService.removeAll(loginUser.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (delete > 0) {
            return "redirect:/cart/list.do";
        } else {
            return "redirect:/";
        }
    }
}
