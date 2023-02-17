package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.OrderServiceImp;
import com.example.gagooda_project.service.RefundServiceImp;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;
    OrderServiceImp orderServiceImp;

    public RefundController(RefundServiceImp refundServiceImp, OrderServiceImp orderServiceImp) {
        this.refundServiceImp = refundServiceImp;
        this.orderServiceImp =  orderServiceImp;
    }

    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       @SessionAttribute(required = false) String refundMsg,
                       HttpSession session,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       @RequestParam(name = "startDate", required = false) String startDate,
                       @RequestParam(name = "endDate", required = false) String endDate,
                       @RequestParam(name = "detCode", required = false) String detCode,
                       Model model){

        if( refundMsg != null){
            session.removeAttribute(refundMsg);
        }

        List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
        List<RefundDto> refundList = refundServiceImp.showUserRefundList(loginUser.getUserId(), period, startDate, endDate, detCode);
        Map<Integer, String> optionNameMap = refundProductMap(refundList);
        model.addAttribute("refundList", refundList);
        model.addAttribute("rfDetList", rfDetList);
        model.addAttribute("optionNameMap", optionNameMap);
        return "refund/user/list";
    }

    @GetMapping("user_yes/mypage/register.do")
    public String register(@SessionAttribute(required = true) UserDto loginUser,
                           @SessionAttribute(required = false) String refundMsg,
                            HttpSession session,
                            @RequestParam(name = "orderId") String orderId,
                            Model model){
        if( refundMsg != null){
            session.removeAttribute(refundMsg);
        }

        OrderDto order = orderServiceImp.selectOne(orderId);
        List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
        AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());

        model.addAttribute("order", order);
        model.addAttribute("orderAddress", orderAddress);
        model.addAttribute("addressList", addressList);
        return "refund/user/register";
    }
    @PostMapping("user_yes/mypage/register.do")
    public String register(RefundDto refund,
                           @SessionAttribute UserDto loginUser,
                           @RequestParam String orderId,
                           HttpSession session){
        int register = 0;
        if (loginUser.getUserId() == refund.getUserId() && refund.getOrderId().equals(orderId)){
            try{
                register = refundServiceImp.registerOne(refund);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(register > 0){
            System.out.println("환불 요청 완료!!!!");
            session.setAttribute("refundMsg", "환불 요청이 완료되었습니다.");
            return "redirect:refund/user_yes/mypage/list.do";
        }else{
            session.setAttribute("refundMsg", "환불 요청에 실패하였습니다.");
            return "redirect:refund/user_yes/register.do?"+refund.getOrderId();
        }
    }

    @GetMapping("user_yes/mypage/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @RequestParam int refundId,
                         Model model){
        RefundDto refund = refundServiceImp.selectOne(refundId);
        List<RefundDto> refundList = new ArrayList<>();
        List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
        List<CommonCodeDto> rfrDetList = refundServiceImp.showDetCodeList("rfr");
        refundList.add(refund);
        OrderDto order = orderServiceImp.selectOne(refund.getOrderId());
        Map<Integer, String> optionNameList = refundProductMap(refundList);
        model.addAttribute("refund", refund);
        model.addAttribute("rfDetList", rfDetList);
        model.addAttribute("rfrDetList", rfrDetList);
        model.addAttribute("order", order);
        model.addAttribute("optionNameList", optionNameList);
        return "refund/user/detail";
    }

    private Map<Integer, String> refundProductMap(List<RefundDto> refundList){
        Map<Integer, String> refundOptName = new HashMap<>();
        for (RefundDto refund : refundList){
            if (refund.getOrderDto().getOrderDetailList() != null){
                for(int i = 0; i < refund.getOrderDto().getOrderDetailList().size(); i++){
                    if(refund.getOrderDetailId() == refund.getOrderDto().getOrderDetailList().get(i).getOrderDetailId()){
                        refundOptName.put(refund.getRefundId(), refund.getOrderDto().getOrderDetailList().get(i).getOptionName());
                    }
                    if (refund.getOrderDetailId() == 0){
                        refundOptName.put(refund.getRefundId(),
                                refund.getOrderDto().getOrderDetailList().get(0).getOptionName() + " 외 " + (refund.getOrderDto().getOrderDetailList().size() -1) + " 건 " );
                    }
                }
            }
        }
        return refundOptName;
    }

}
