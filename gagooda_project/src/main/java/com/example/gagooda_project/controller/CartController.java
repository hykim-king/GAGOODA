package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String list(Model model,
                       @RequestParam(name = "userId", required = false) int userId,
                       @RequestParam(name = "optionCode", required = false) String optionCode) {
        List<CartDto> cartList = cartService.cartList(userId);
        CartDto cart = cartService.selectOne(userId, optionCode);
        int cnt = 0;
        cnt += cartService.countCartItems(userId);
        model.addAttribute("cartList", cartList);
        model.addAttribute("cart", cart);
        model.addAttribute("cnt", cnt);
        return "/cart/list";
    }

    @GetMapping("/deleteOne.do")
    public String deleteOne(@RequestParam(name = "cartId") int cartId) {
        int delete = 0;
        try {
            delete = cartService.removeOne(cartId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/list.do";
    }

    @GetMapping("/deleteAll.do")
    public String deleteAll(@RequestParam(name = "userId") int userId) {
        int delete = 0;
        try {
            delete = cartService.removeAll(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/list.do";
    }
}
