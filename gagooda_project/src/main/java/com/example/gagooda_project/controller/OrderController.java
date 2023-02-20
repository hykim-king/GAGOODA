package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequestMapping("/order")
@Controller
public class OrderController {
    private OrderService orderService;
    private CartService cartService;

    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderController(OrderService orderService,
                           CartService cartService){
        this.cartService = cartService;
        this.orderService = orderService;
    }


    @GetMapping("/user_yes/mypage/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req,
                       @RequestParam(name = "dates", defaultValue = "7", required = false) int dates,
                       @SessionAttribute UserDto loginUser){
        log.info("req.getParameterMap(): "+req.getParameterMap());
        log.info("nameS:" + req.getParameterNames());
        System.out.println(paging);
        List<OrderDto> orderList = null;
        try{
            paging.setQueryString(req.getParameterMap());
            orderList = orderService.orderList(paging, loginUser.getUserId(), dates);
            model.addAttribute("orderList",orderList);
            model.addAttribute("paging",paging);
            log.info("paging.toString(): "+paging.toString());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if (orderList != null) {
            return "/order/list";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/user_yes/{orderId}/detail.do")
    public String detail(@SessionAttribute(required = true) UserDto loginUser,
                         @PathVariable String orderId,
                         Model model){
        OrderDto order = orderService.selectOne(orderId);
        List<OrderDetailDto> orderDetailList = orderService.orderDetailList(orderId);
        DeliveryDto delivery = orderService.selectDelivery(orderId);
        model.addAttribute("order",order);
        model.addAttribute("orderDetailList",orderDetailList);
        model.addAttribute("delivery", delivery);
        return "/order/detail";
    }
    @GetMapping("/user_yes/{orderId}/complete.do")
    public String complete(@SessionAttribute(required=true) UserDto loginUser,
                           @PathVariable String orderId,
                           Model model){
        OrderDto order=orderService.selectOne(orderId);
        List<OrderDetailDto> orderDetailList = orderService.orderDetailList(orderId);
        model.addAttribute("totalPrice",order.getTotalPrice());
        model.addAttribute("orderDetailList",orderDetailList);
        return "/order/complete";
    }
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @SessionAttribute(required = false) String msg,
                           HttpSession session,
                           Model model,
                           @RequestParam List<Integer> orderCartIds
    ){
        List<AddressDto> addressList = null;
        List<CartDto> cartList = new ArrayList<>();
        System.out.println(loginUser.getUserId());
        try {
            for (int i=0; i<orderCartIds.size(); i++) {
                CartDto cartDto = new CartDto();
                cartDto = cartService.selectByCartId(orderCartIds.get(i));
                cartList.add(cartDto);
            }
            addressList = orderService.userAddressList(loginUser.getUserId());
            log.info("user.addressList: "+addressList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UUID orderRandom = UUID.randomUUID();
        String randomString = orderRandom.toString();
        randomString = randomString.replace("-","").substring(0,8);
        if(addressList != null){
            model.addAttribute("addressList", addressList);
        }
        if (cartList != null) {
            model.addAttribute("cartList",cartList);
            model.addAttribute("loginUser",loginUser);
            model.addAttribute("ranString", randomString);
            return "/order/register";
        } else {
            return "redirect:/";
        }
    }
    @PostMapping("/user_yes/register.do")
    public String register(
            OrderDto order,
            @SessionAttribute UserDto loginUser,
            @RequestParam(required = false, name="delivery.request") String request,
            @RequestParam(required = true, name="cartItem") List<String> cartList,
            HttpSession session
            ){
        System.out.println(order);
        System.out.println(cartList);
        int register = 0;
        System.out.println("loginUser:"+loginUser.getUserId());
        System.out.println("orderDto:"+order.getUserId());
        if(loginUser.getUserId()==(order.getUserId())){
            List<OrderDetailDto> orderDetailList = null;
            OrderDetailDto orderDetail = null;
            AddressDto address = null;
            DeliveryDto delivery = null;
            log.info("1.register에 진입");
            try{
                orderDetailList = new ArrayList<>();
                for(String cartIdStr:cartList){
                    log.info("2. cart forloop에 들어옴");
                    int cartId = Integer.parseInt(cartIdStr);
                    CartDto cart = orderService.selectByCartId(cartId);
                    orderDetail = new OrderDetailDto();
                    orderDetail.setProductCode(cart.getProductCode());
                    orderDetail.setOptionCode(cart.getOptionCode());
                    orderDetail.setOptionName(cart.getOptionProduct().getOpname());
                    orderDetail.setCnt(cart.getCnt());
                    orderDetail.setPrice(cart.getOptionProduct().getPrice());
                    int cartPrice = cart.getOptionProduct().getPrice();
                    int cartCount = cart.getCnt();
                    orderDetail.setTotalPrice(cartPrice * cartCount);
                    orderDetail.setOrderId(order.getOrderId());
                    orderDetailList.add(orderDetail);
                    log.info("3.orderdetail에 등록 성공");

                }
                delivery = new DeliveryDto();
                delivery.setRequest(request);
                delivery.setOrderId(order.getOrderId());
                order.setOrderDetailList(orderDetailList);
                log.info("4.order에 address등록 완료");
                register = orderService.register(order,delivery);
                log.info("5.orderservice.register 완료");
            }catch(Exception e){
                log.error(e.getMessage());
            }


        }
        log.info("6.register 등록 여부 확인");
        System.out.println("999.register: " + register);
        if(register>0){
            log.info("주문 등록 완료!");
            return "redirect:/order/user_yes/"+order.getOrderId()+"/complete.do";

        }else{
            session.setAttribute("orderMsg","주문을 완료하지 못했습니다. 다시 시도해주세요.");
            return "redirect:/order/user_yes/register.do";
        }

    }

}
