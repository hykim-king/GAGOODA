package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.OrderDetailDto;
import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequestMapping("/order")
@Controller
public class OrderController {
    private OrderService orderService;

    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderController(OrderService orderService){

        this.orderService = orderService;
    }

    @GetMapping("/user_yes/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req,
                       @SessionAttribute UserDto loginUser){
        paging.setRows(4);
        paging.setQueryString(req.getParameterMap());
        log.info("req.getParameterMap(): "+req.getParameterMap());
        List<OrderDto> orderList = orderService.orderList(paging, loginUser.getUserId());
        model.addAttribute("orderList",orderList);
        model.addAttribute("paging",paging);
        log.info(paging.toString());
        return "/order/list";
    }
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                         @SessionAttribute(required = false) String msg,
                         HttpSession session,
                         Model model
    ){
        if (msg !=null){
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "/order/register";
    }
    @PostMapping("/user_yes/register.do")
    public String register(
            OrderDto orderDto,
            OrderDetailDto orderDetailDto,
            @SessionAttribute UserDto loginUser,
            @RequestParam(name="cartId") List<Integer> cartIdList
            ){
        System.out.print("register 진입");
        return null;
    }

}
