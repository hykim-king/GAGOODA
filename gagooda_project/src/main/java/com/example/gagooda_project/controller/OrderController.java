package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.AddressService;
import com.example.gagooda_project.service.CartServiceImp;
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


@RequestMapping("/order")
@Controller
public class OrderController {
    private OrderService orderService;
    private AddressService addressService;
    private CartServiceImp cartService;

    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderController(OrderService orderService,
                           AddressService addressService,
                           CartServiceImp cartService){

        this.orderService = orderService;
        this.addressService = addressService;
        this.cartService = cartService;
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
        model.addAttribute("msg", msg);

        List<AddressDto> addressList = addressService.addressList(loginUser.getUserId());
        List<CartDto> cartList = cartService.cartList(loginUser.getUserId());
        model.addAttribute("addressList", addressList);
        model.addAttribute("cartList",cartList);
        model.addAttribute("loginUser",loginUser);
        return "/order/register";
    }
    @PostMapping("/user_yes/register.do")
    public String register(
            OrderDto order,
            @SessionAttribute UserDto loginUser,
            @RequestParam(required = false, name="selectedAddressId") int addressId,
            @RequestParam(required = false, name="delivery.request") String request,
            @RequestParam(required = true, name="cartItem") List<String> cartList
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
                orderDetailList = new ArrayList<OrderDetailDto>();
                for(String cartIdStr:cartList){
                    log.info("2. cart forloop에 들어옴");
                    int cartId = Integer.parseInt(cartIdStr);
                    CartDto cart = cartService.selectByCartId(cartId);
                    orderDetail = new OrderDetailDto();
                    orderDetail.setProductCode(cart.getProductCode());
                    orderDetail.setOptionCode(cart.getOptionCode());
                    orderDetail.setOptionName(cart.getOptionProduct().getOpname());
                    orderDetail.setCnt(cart.getCnt());
                    orderDetail.setPrice(cart.getOptionProduct().getPrice());
                    orderDetail.setTotalPrice(cart.getOptionProduct().getPrice()*cart.getCnt());
                    orderDetail.setOrderId(order.getOrderId());
                    orderDetailList.add(orderDetail);
                    log.info("3.orderdetail에 등록 성공");

                }
                address = addressService.selectOne(addressId);
                delivery.setRequest(request);
                delivery.setOrderId(order.getOrderId());
                order.setAddressId(address.getAddressId());
                order.setPostCode(address.getPostCode());
                order.setAddress(address.getAddress());
                order.setAddressDetail(address.getAddressDetail());
                order.setReceiverName(address.getReceiverName());
                order.setReceiverPhone(address.getReceiverPhone());
                order.setElevator(address.isElevator());
                order.setOrderDetailList(orderDetailList);
                log.info("4.order에 address등록 완료");
                register = orderService.register(order,delivery);
                log.info("5.orderservice.register 완료");
            }catch(Exception e){
                log.error(e.getMessage());
            }


        }
        log.info("5.register 등록 여부 확인");
        if(register>0){
            return "redirect:/order/user_yes/list.do";
        }else{
            return "redirect:/order/user_yes/register.do";
        }

    }

}
