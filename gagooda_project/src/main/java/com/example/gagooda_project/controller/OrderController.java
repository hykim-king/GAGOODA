package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.AddressService;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.OrderService;
import com.example.gagooda_project.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequestMapping("/order")
@Controller
public class OrderController {
    private OrderService orderService;
    private CartService cartService;
    private AddressService addressService;

    private Logger log= LoggerFactory.getLogger(this.getClass().getSimpleName());

    public OrderController(OrderService orderService,
                           CartService cartService,
                           AddressService addressService){
        this.cartService = cartService;
        this.orderService = orderService;
        this.addressService = addressService;
    }

    @GetMapping("/admin/list.do")
    public String adminList(@SessionAttribute UserDto loginUser,
                            PagingDto paging,
                            HttpServletRequest req,
                            HttpSession session,
                            @SessionAttribute(required = false) String msg,
                            @RequestParam(name = "oDet", required = false, defaultValue = "") String oDet,
                            @RequestParam(name = "searchDiv", required = false, defaultValue = "") String searchDiv,
                            @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
                            @RequestParam(name = "dateType", required = false, defaultValue = "") String dateType,
                            @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                            @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate,
                            Model model){

        log.info("req.getParameterMap:"+req.getParameterMap());
        String adminMsg = "";
        if (session.getAttribute(msg) != null){
            adminMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        try{
            if (loginUser.getGDet().equals("g1")){
                Map<String, Object> searchFilter = new HashMap<>();
                paging.setQueryString(req.getParameterMap());
                searchFilter.put("oDet",oDet); searchFilter.put("searchDiv",searchDiv); searchFilter.put("searchWord", searchWord);
                searchFilter.put("dateType", dateType); searchFilter.put("startDate", startDate); searchFilter.put("endDate", endDate);
                searchFilter.put("paging", paging);
                log.info("searchFilter: "+searchFilter);
                List<CommonCodeDto> oCodeList = orderService.showDetCodeList("o");
                List<OrderDto> orderList = orderService.showOrderList(searchFilter);
                int oCount = orderService.countPageAll(searchFilter);
                log.info("oCount: "+ oCount);
//                int allRfCnt = refundServiceImp.countAll();
                log.info("orderList:"+ orderList.toString()+"$$$$$$$$$$$$$$$$$$$$$");
                model.addAttribute("orderList", orderList);
                model.addAttribute("oCodeList", oCodeList);
                model.addAttribute("paging",paging);
                model.addAttribute("oCount", oCount);
                model.addAttribute("msg",msg);
//                model.addAttribute("allCount",allRfCnt);
                return "order/admin/list";
            }
            else{
                return "/index";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "refund/admin/list";
    }
    @PostMapping("/admin/modify.do")
    public String adminModify( @RequestParam(required = true, name="orderIdHidden") List<String> orderIdList,
                               @RequestParam(required = true, name="oDetHidden") List<String> oDetList,
                               HttpSession session){
        int register = 0;
        log.info("ORDERIDLIST: "+orderIdList);
        log.info("ODETLIST: "+oDetList);
        try{
            register = orderService.adminModify(orderIdList, oDetList);
            log.info("1.register 끝");

        }catch(Exception e){
            log.error(e.getMessage());
        }
        log.info("register value: "+register);
        if(register>0){
            log.info("2.register 성공");
            session.setAttribute("msg","주문상태 변경에 성공하였습니다.");
            return "redirect:/order/admin/list.do";
        }else{
            log.info("3.register 실패");
            session.setAttribute("msg","주문 상태 변경에 실패하였습니다.");
            return "redirect:/order/admin/list.do";
        }

    }
    @GetMapping("/admin/{orderId}/detail.do")
    public String adminDetail(@PathVariable String orderId,
                              Model model,
                              HttpSession session,
                              @SessionAttribute(required = false) String msg){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        OrderDto order = orderService.selectOne(orderId);
        List<OrderDetailDto> orderDetailList = orderService.orderDetailList(orderId);
        DeliveryDto delivery = orderService.selectDelivery(orderId);
        List<CommonCodeDto> oCodeList = orderService.showDetCodeList("o");
        model.addAttribute("order",order);
        model.addAttribute("orderDetailList",orderDetailList);
        model.addAttribute("delivery", delivery);
        model.addAttribute("oCodeList",oCodeList);
        return "/order/admin/detail";
    }

//    사용자 주문 목록
    @GetMapping("/user_yes/mypage/list.do")
    public String list(Model model,
                       PagingDto paging,
                       HttpServletRequest req,
                       @RequestParam(name = "dates", defaultValue = "7", required = false) int dates,
                       @SessionAttribute UserDto loginUser,
                       HttpSession session,
                       @SessionAttribute(required = false) String msg){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        log.info("req.getParameterMap(): "+req.getParameterMap());
        log.info("nameS:" + req.getParameterNames());
        System.out.println(paging);
        List<OrderDto> orderList = null;
        try{
            paging.setQueryString(req.getParameterMap());
            orderList = orderService.orderList(paging, loginUser.getUserId(), dates);
            List<CommonCodeDto> oCodeList = orderService.showDetCodeList("o");
            model.addAttribute("orderList",orderList);
            model.addAttribute("paging",paging);
            model.addAttribute("oCodeList",oCodeList);
            model.addAttribute("dates",dates);
            log.info("paging.toString(): "+paging.toString());
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if (orderList != null) {
            return "/order/user/list";
        } else {
            return "redirect:/";
        }
    }
//    사용자 주문 상세 보기
    @GetMapping("/user_yes/mypage/{orderId}/detail.do")
    public String detail(@SessionAttribute(required = true) UserDto loginUser,
                         @PathVariable String orderId,
                         Model model,
                         HttpSession session,
                         @SessionAttribute(required = false) String msg){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        try{
            OrderDto order = orderService.selectOne(orderId);
            List<OrderDetailDto> orderDetailList = orderService.orderDetailList(orderId);
            DeliveryDto delivery = orderService.selectDelivery(orderId);
            List<CommonCodeDto> oCodeList = orderService.showDetCodeList("o");
            int rfCount = orderService.countRefund(orderId);
            model.addAttribute("order",order);
            model.addAttribute("orderDetailList",orderDetailList);
            model.addAttribute("delivery", delivery);
            model.addAttribute("oCodeList",oCodeList);
            model.addAttribute("rfCount",rfCount);
        }catch(Exception exception){
            log.error(exception.getMessage());
        }

        return "/order/user/detail";
    }
    /*사용자 주문 취소로 주문 상태 수정*/
    @GetMapping("/user_yes/mypage/{orderId}/modify.do")
    public String modify(@SessionAttribute(required = true) UserDto loginUser,
                         @PathVariable String orderId,
                         HttpSession session,
                         @SessionAttribute(required = false) String msg){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        int modify = 0;
        String oDet = "o5";
        try{
            OrderDto order = orderService.selectOne(orderId);
            modify = orderService.modifyOne(orderId,oDet);

        }catch(Exception e){
            log.error(e.getMessage());
        }
        if (modify>0){
            return "redirect:/order/user_yes/mypage/list.do";
        }else{
            return "redirect:/order/user_yes/mypage/"+orderId+"/detail.do";

        }
    }
    /*주문 완료*/
    @GetMapping("/user_yes/{orderId}/complete.do")
    public String complete(@SessionAttribute(required=true) UserDto loginUser,
                           @PathVariable String orderId,
                           Model model,
                           HttpSession session,
                           @SessionAttribute(required = false) String msg){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        OrderDto order=orderService.selectOne(orderId);
        List<OrderDetailDto> orderDetailList = orderService.orderDetailList(orderId);
        int itemCount = orderDetailList.size();
        model.addAttribute("totalPrice",order.getTotalPrice());
        model.addAttribute("orderDetailList",orderDetailList);
        model.addAttribute("itemCount",itemCount);
        return "/order/user/complete";
    }
    /*주문 등록 새 배송지 등록*/
    @PostMapping("/user_yes/addressRegister.do")
    public @ResponseBody AddressDto addressRegister(@SessionAttribute UserDto loginUser,
                                             AddressDto address,
                                             HttpSession session){
        int register = 0;
        int addressId;
        try{
            register = addressService.register(address);
        }catch(Exception e){
            log.error(e.getMessage());
            address=null;
        }
        if(register >0 ){
            addressId = address.getAddressId();
            session.setAttribute("addressId",addressId);
            session.setAttribute("msg","새로운 배송지가 등록되었습니다.");
        }else{
            session.setAttribute("msg","배송지 등록에 실패하였습니다. 다시 시도해주세요.");
            address = null;
        }
        return address;
    }
    /*새 배송지 등록 리스트*/
    @GetMapping("/user_yes/addressList.do")
    public String addressList(@SessionAttribute UserDto loginUser,
                                            HttpSession session,
                                            @SessionAttribute(required = false) String msg,
                                            Model model){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
            orderMsg = session.getAttribute(msg).toString();
            session.removeAttribute(msg);
        }
        AddressDto address = null;
        List<AddressDto> addressList = null;
        int addressId=0;
        try{
            if (session.getAttribute("addressId") != null){
                addressId = (int) session.getAttribute("addressId");
                log.info("addressId"+session.getAttribute("addressId"));
                session.removeAttribute("addressId");
                address = addressService.selectOne(addressId);
                log.info("newAddress: "+address);
                model.addAttribute("addressId",addressId);
                model.addAttribute("address",address);
            }
            addressList = orderService.userAddressList(loginUser.getUserId());
            if(addressList != null){
                model.addAttribute("addressList", addressList);
            }

        }catch(Exception e){
            log.error(e.getMessage());
        }
        return "order/user/newAddressList";
    }
    /*주문 등록 (GET)*/
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @SessionAttribute(required = false) String msg,
                           HttpSession session,
                           Model model,
                           @RequestParam List<Integer> orderCartIds
    ){
        String orderMsg = "";
        if (session.getAttribute(msg) != null){
           orderMsg = session.getAttribute(msg).toString();
           session.removeAttribute(msg);
        }
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
        randomString = randomString.replace("-","").substring(0,10);
        if(addressList != null){
            model.addAttribute("addressList", addressList);
        }
        if (cartList != null) {
            model.addAttribute("cartList",cartList);
            model.addAttribute("loginUser",loginUser);
            model.addAttribute("ranString", randomString);
            model.addAttribute("orderMsg",orderMsg);
            return "/order/user/register";
        } else {
            return "redirect:/";
        }
    }
    /*주문 등록 (POST)*/
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
                delivery.setUserId(order.getUserId());
                delivery.setUserName(order.getUserName());
                delivery.setUserEmail(order.getUserEmail());
                delivery.setUserPhone(order.getUserPhone());
                log.info("deliveryDto: "+delivery);
                order.setOrderDetailList(orderDetailList);
                log.info("4.order에 address등록 완료");
                register = orderService.register(order,delivery,cartList);
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
            session.setAttribute("msg","주문을 완료하지 못했습니다. 다시 시도해주세요.");
            return "redirect:/order/user_yes/register.do";
        }

    }
}
