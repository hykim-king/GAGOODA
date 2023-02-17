package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CartServiceImp;
import com.example.gagooda_project.service.ProductServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    CartServiceImp cartServiceImp;
    ProductServiceImp productServiceImp;

    public CartController(CartServiceImp cartServiceImp, ProductServiceImp productServiceImp) {
        this.cartServiceImp = cartServiceImp;
        this.productServiceImp = productServiceImp;
    }

    @GetMapping("/list.do")
    public String list(@RequestParam(name = "optionCode", required = false) String optionCode,
                       Model model,
                       @SessionAttribute UserDto loginUser) throws Exception {
        List<CartDto> cartList = cartServiceImp.cartList(loginUser.getUserId());
        CartDto cart = cartServiceImp.selectOne(loginUser.getUserId(), optionCode);
        int totalCnt = 0;
        int totalPrice = 0;
        for (CartDto cartDto : cartList) {
            totalCnt += cartDto.getCnt();
            totalPrice += cartDto.getOptionProduct().getPrice()*totalCnt;
        }
        model.addAttribute("cartList", cartList);
        model.addAttribute("cart", cart);
        model.addAttribute("totalCnt", totalCnt);
        model.addAttribute("totalPrice", totalPrice);
        return "/cart/list";
    }

    @PostMapping("/update.do")
    public String update(CartDto cart,
                         @SessionAttribute UserDto loginUser) throws Exception {
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
            delete = cartServiceImp.removeOne(Math.toIntExact(id));
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
            delete = cartServiceImp.removeAll(loginUser.getUserId());
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
