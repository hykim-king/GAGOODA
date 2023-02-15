package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.ExchangeDto;
import com.example.gagooda_project.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    private ExchangeService exchangeService;
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/list.do")
    public String list() {

        return "/exchange/list";
    }
    @PostMapping("/register.do")
    public void register(ExchangeDto exchange) {

    }
    @GetMapping("/detail.do")
    public String detail() {
        return "/exchange/detail";
    }
    @GetMapping("/{exchangeId}/modify.do")
    public String modify(@PathVariable int exchangeId) {
//        ExchangeDto exchange = exchangeService.updateOne();
        return "/exchange/modify";
    }

    @PostMapping("/modify.do")
    public String modify(ExchangeDto exchange) {
        int modify = 0;
        if (modify > 0) {
            return "redirect:/exchange/"+exchange.getExchangeId()+"/detail.do";
        } else {
            return "redirect:/exchange/"+exchange.getExchangeId()+"/modify.do";
        }
    }
}
