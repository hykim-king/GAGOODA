package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    ExchangeService exchangeService;
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/user/list.do")
    /* /exchange/list.do?userId=?&period=? */
    public String list(
            @SessionAttribute UserDto loginUser,
            @RequestParam(name = "period", defaultValue = "7", required = false) int period,
            Model model
    ) {
        List<ExchangeDto> exchangeList = exchangeService.orderInDate(loginUser.getUserId(), period);
        model.addAttribute("exchangeList", exchangeList);
        return "/exchange/user/list";
    }
    @GetMapping("/user/register.do")
    /* /exchange/register.do?orderId=?&userId=? */
    public String register(@SessionAttribute UserDto loginUser,
                           @RequestParam(name = "orderId", required = false) String orderId,
                           Model model
                           ) {
        return "/exchange/user/register";
    }
    @PostMapping("/user/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            ExchangeDto exchange,
            Model model) {
        int register = 0;
        register = exchangeService.register(exchange);
        if (register > 0) {
            return "redirect:/exchange/user/detail.do?exchangeId="+exchange.getExchangeId();
        } else {
            return "redirect:/exchange/user/list.do";
        }
    }

    @GetMapping("/detail.do")
    public String detail() {
        return "/exchange/user/detail";
    }

    @GetMapping("/{exchangeId}/modify.do")
    public String modify(@PathVariable int exchangeId) {
//        ExchangeDto exchange = exchangeService.;
        return "/exchange/modify";
    }

    @PostMapping("/modify.do")
    public String modify(ExchangeDto exchange) {
        int modify = 0;
        try {

        } catch (Exception e) {
            e.getStackTrace();
        }
        if (modify > 0) {
            return "redirect:/exchange/" + exchange.getExchangeId() + "/detail.do";
        } else {
            return "redirect:/exchange/" + exchange.getExchangeId() + "/modify.do";
        }
    }
}
