package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.OptionProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    CartService cartService;
    OptionProductService optionProductService;

    public CartController(CartService cartService, OptionProductService optionProductService) {
        this.cartService = cartService;
        this.optionProductService = optionProductService;
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

    @PostMapping("/user_yes/register.do")
    public String insertInto(
            @SessionAttribute UserDto loginUser,
            CartDto cart,
            HttpSessionat session
    ) {
        System.out.println(loginUser);
        System.out.println(cart);
        int insert = 0;
        if (loginUser.getUserId() == cart.getUserId()) {
            try {
                OptionProductDto optionProduct = optionProductService.selectOne(cart.getOptionCode());
                if (cart.getCnt() > optionProduct.getStock()) {
                    session.setAttribute("msg", "장바구니에 담는데 문제가 있었습니다.");
                    return "redirect:/product/" + cart.getProductCode() + "/detail.do";
                }
                CartDto cartIn = cartService.selectOne(loginUser.getUserId(), cart.getOptionCode());
                if (cartIn != null) {
                    cartIn.setCnt(cartIn.getCnt() + cart.getCnt());
                    System.out.println("cartIn " + cartIn);
                    insert = cartService.modifyOne(cartIn);
                } else {
                    cart.setUserId(loginUser.getUserId());
                    insert = cartService.registerOne(cart);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("insert cart: "+insert);
            if (!(insert > 0)) {
                session.setAttribute("msg", "장바구니에 담는데 문제가 있었습니다.");
            } else {
                session.setAttribute("msg", "장바구니에 성공적으로 담았습니다.");
            }
        } else {
            session.setAttribute("msg", "본인 장바구니가 아닙니다.");
        }
        return "redirect:/product/" + cart.getProductCode() + "/detail.do";
    }
}
