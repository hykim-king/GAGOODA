package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.ExchangeService;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {
    ExchangeService exchangeService;
    OrderService orderService;

    public ExchangeController(ExchangeService exchangeService, OrderService orderService) {
        this.exchangeService = exchangeService;
        this.orderService = orderService;
    }

    @GetMapping("user_yes/mypage/list.do")
    /* /exchange/list.do?userId=?&period=? */
    public String list(
            @SessionAttribute UserDto loginUser,
            @SessionAttribute(required = false) String exchangeMsg,
            HttpSession session,
            @RequestParam(name = "period", defaultValue = "7", required = false) int period,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "detCode", required = false) String detCode,
            Model model
    ) {
        if (exchangeMsg != null) {
            session.removeAttribute(exchangeMsg);
        }

        List<ExchangeDto> exchangeList = exchangeService.orderInDate(loginUser.getUserId(), period);
        List<CommonCodeDto> exDetList = exchangeService.detCodeList("ex");
        Map<Integer, String> optionNameMap = exchangeProductMap(exchangeList);
        model.addAttribute("exchangeList", exchangeList);
        model.addAttribute("exDetList", exDetList);
        model.addAttribute("optionNameMap", optionNameMap);
        return "exchange/user/list";
    }

    private Map<Integer, String> exchangeProductMap(List<ExchangeDto> exchangeList) {
        Map<Integer, String> exchangeOptName = new HashMap<>();
        for (ExchangeDto exchange : exchangeList) {
            if (exchange.getOrderDto().getOrderDetailList() != null) {
                for (int i = 0; i < exchange.getOrderDto().getOrderDetailList().size(); i++) {
                    if(exchange.getOrderDetailId() == exchange.getOrderDto().getOrderDetailList().get(i).getOrderDetailId()){
                        exchangeOptName.put(exchange.getExchangeId(), exchange.getOrderDto().getOrderDetailList().get(i).getOptionName());
                    }
                    if (exchange.getOrderDetailId() == 0){
                        exchangeOptName.put(exchange.getExchangeId(),
                                exchange.getOrderDto().getOrderDetailList().get(0).getOptionName() + " 외 " + (exchange.getOrderDto().getOrderDetailList().size() -1) + " 건 " );
                    }
                }
            }
        }
        return exchangeOptName;
    }

    @GetMapping("user_yes/mypage/register.do")
    /* /exchange/register.do?orderId=?&userId=? */
    public String register(@SessionAttribute UserDto loginUser,
                           @SessionAttribute(required = false) String exchangeMsg,
                           @RequestParam(name = "orderId") String orderId,
                           HttpSession session,
                           Model model
    ) {
        if (exchangeMsg != null) {
            session.removeAttribute(exchangeMsg);
        }
        OrderDto order = orderService.selectOne(orderId);
        List<AddressDto> addressList = exchangeService.showAddressListByUserId(loginUser.getUserId());
        AddressDto orderAddress = exchangeService.selectAddress(order.getAddressId());

        model.addAttribute("order", order);
        model.addAttribute("orderAddress", orderAddress);
        model.addAttribute("addressList", addressList);
        return "exchange/user/register";
    }

    @PostMapping("user_yes/mypage/register.do")
    public String register(
            @SessionAttribute UserDto loginUser,
            @RequestParam String orderId,
            ExchangeDto exchange,
            HttpSession session) {
        int register = 0;
        if (loginUser.getUserId() == exchange.getUserId() && exchange.getOrderId().equals(orderId)) {
            try {
                register = exchangeService.register(exchange);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        if (register > 0) {
            session.setAttribute("exchangeMsg", "교환 요청이 완료되었습니다.");
            return "redirect:/exchange/user_yes/mypage/list.do";
        } else {
            session.setAttribute("exchangeMsg", "교환 요청이 실패하였습니다.");
            return "redirect:/exchange/user_yes/register.do?"+exchange.getOrderId();
        }
    }

    @GetMapping("user_yes/mypage/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @RequestParam int exchangeId,
                         Model model) {
        ExchangeDto exchange = exchangeService.selectOne(exchangeId);
        List<ExchangeDto> exchangeList = new ArrayList<>();
        List<CommonCodeDto> exDetList = exchangeService.detCodeList("ex");
        List<CommonCodeDto> rfrDetList = exchangeService.detCodeList("rfr");
        exchangeList.add(exchange);
        OrderDto order = orderService.selectOne(exchange.getOrderId());
        Map<Integer, String> optionNameList = exchangeProductMap(exchangeList);
        model.addAttribute("exchange", exchange);
        model.addAttribute("exDetList", exDetList);
        model.addAttribute("rfrDetList", rfrDetList);
        model.addAttribute("order", order);
        model.addAttribute("optionNameList", optionNameList);
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
