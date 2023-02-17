package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.ProductService;
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
        List<CartDto> cartList = cartService.cartList(loginUser.getUserId());
        CartDto cart = cartService.selectOne(loginUser.getUserId(), optionCode);
        model.addAttribute("cartList", cartList);
        model.addAttribute("cart", cart);
        return "/cart/list";
    }

    @PostMapping("/deleteOne.do")
    public String deleteOne(CartDto cart,
                            @RequestParam List<Integer> cartIds,
                            @SessionAttribute UserDto loginUser) throws Exception {
//        int delete = 0;
//        try {
//            delete = cartService.removeOne(cart.getCartId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (delete > 0) {
//            return "redirect:/cart/list.do";
//        } else {
//            return "redirect:/";
//        }
        int delete = 0;
        for (int i=0; i<cartIds.size(); i++) {
            Long id = Long.valueOf(cartIds.get(i));
            delete = cartService.removeOne(Math.toIntExact(id));
        }
        if (delete > 0) {
            return "redirect:/cart/list.do";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/deleteAll.do")
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
