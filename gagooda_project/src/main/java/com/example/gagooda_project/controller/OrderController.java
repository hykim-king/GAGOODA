package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.OrderDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/order")
@Controller
public class OrderController {
    private OrderService orderService;

    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req
                       ){
        paging.setQueryString(req.getParameterMap());
        List<OrderDto> orderList = orderService.orderList(paging);

        return null;
    }

}
