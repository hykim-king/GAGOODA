package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.OptionProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    CartServiceImp cartServiceImp;
    OptionProductService optionProductService;
    ProductService prductService;
    ImageService imageService;

    public CartController(CartServiceImp cartServiceImp,
                          ProductService prductService,
                          OptionProductService optionProductService,
                          ImageService imageService) {
        this.cartServiceImp = cartServiceImp;
        this.prductService = prductService;
        this.optionProductService = optionProductService;
        this.imageService = imageService;
    }

    @Value("${img.upload.path}")
    private String imgPath;

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
    public String update(@RequestParam(name = "optionCode", required = false) String optionCode,
                         CartDto cart,
                         @RequestParam List<Integer> cartCnts,
                         @SessionAttribute UserDto loginUser) throws Exception {
        int update = 0;
        try {
            int i = 0;
            List<CartDto> cartList = cartServiceImp.cartList(loginUser.getUserId());
            for (CartDto cartDto : cartList) {
                int cnt = Integer.parseInt(String.valueOf(cartCnts.get(i)));
                cartDto.setCnt(cnt);
                update = cartServiceImp.modifyOne(cartDto);
                i += 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (update > 0) {
            return "redirect:/cart/list.do";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/deleteOne.do")
    public String deleteOne(CartDto cart,
                            @RequestParam List<Integer> cartIds,
                            @SessionAttribute UserDto loginUser) throws Exception {
        int delete = 0;
        try {
            for (int i=0; i<cartIds.size(); i++){
                int id = Integer.parseInt(String.valueOf(cartIds.get(i)));
                delete = cartServiceImp.removeOne(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @PostMapping("/user_yes/register.do")
    public String insertInto(
            @SessionAttribute UserDto loginUser,
            CartDto cart,
            HttpSession session
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
                CartDto cartIn = cartServiceImp.selectOne(loginUser.getUserId(), cart.getOptionCode());
                if (cartIn != null) {
                    cartIn.setCnt(cartIn.getCnt() + cart.getCnt());
                    System.out.println("cartIn " + cartIn);
                    insert = cartServiceImp.modifyOne(cartIn);
                } else {
                    cart.setUserId(loginUser.getUserId());
                    insert = cartServiceImp.registerOne(cart);
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
