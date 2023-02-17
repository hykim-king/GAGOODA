package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.OrderServiceImp;
import com.example.gagooda_project.service.RefundServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;
    OrderServiceImp orderServiceImp;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public RefundController(RefundServiceImp refundServiceImp, OrderServiceImp orderServiceImp) {
        this.refundServiceImp = refundServiceImp;
        this.orderServiceImp = orderServiceImp;
    }

    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute(required = false) UserDto loginUser,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       @RequestParam(name = "startDate", required = false) String startDate,
                       @RequestParam(name = "endDate", required = false) String endDate,
                       @RequestParam(name = "detCode", required = false) String detCode,
                       HttpSession session,
                       Model model) {
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        try {
            List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
            List<RefundDto> refundList = refundServiceImp.showUserRefundList(loginUser.getUserId(), period, startDate, endDate, detCode);
            int userRefundCount = refundServiceImp.showCountByUser(loginUser.getUserId());
            Map<String, Integer> orderDetailCountMap = new HashMap<>();
            for (RefundDto refund : refundList) {
                orderDetailCountMap.put(refund.getOrderId(), refundServiceImp.CountByOrderId(refund.getOrderId()));
            }
            model.addAttribute("refundList", refundList);
            model.addAttribute("rfDetList", rfDetList);
            model.addAttribute("userRefundCount", userRefundCount);
            model.addAttribute("orderDetailCountMap", orderDetailCountMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("refundMsg", refundMsg);
        return "refund/user/list";
    }

    @GetMapping("user_yes/mypage/register.do/{orderId}")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable String orderId,
                           HttpSession session,
                           Model model) {
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        OrderDto order = orderServiceImp.selectOne(orderId);
        int refundCount = refundServiceImp.countByOrderId(orderId);
        List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
        AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());

        model.addAttribute("order", order);
        model.addAttribute("refundCount", refundCount);
        model.addAttribute("orderAddress", orderAddress);
        model.addAttribute("addressList", addressList);
        model.addAttribute("refundMsg", refundMsg);
        return "refund/user/register";
    }

    @PostMapping("user_yes/mypage/register.do/{orderId}")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable String orderId,
                           HttpSession session,
                           RefundDto refund) {
        int register = 0;
        RefundDto checkDto;
        if (loginUser.getUserId() == refund.getUserId() && refund.getOrderId().equals(orderId)) {
            try {
                register += refundServiceImp.registerOne(refund);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (register > 0) {
            System.out.println("환불 요청 완료!!!!");
            session.setAttribute("refundMsg", "환불 요청에 성공했습니다.");
            return "redirect:/refund/user_yes/mypage/list.do";
        } else {
            session.setAttribute("refundMsg", "환불 요청에 실패했습니다.");
            return "redirect:/refund/user_yes/mypage/register.do/" + orderId;
        }
    }

    @GetMapping("user_yes/mypage/detail.do/{refundId}")
    public String detail(@SessionAttribute UserDto loginUser,
                         @PathVariable int refundId,
                         HttpSession session,
                         Model model) {
        log.info("$$$$$$$$$$$$$$$$$$$$"+session.getAttribute("refundMsg")+"$$$$$$$$$$$$$$");
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        RefundDto refund = refundServiceImp.selectOne(refundId);
        if (loginUser.getUserId() == refund.getUserId()) {
            try {
                List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
                List<CommonCodeDto> rfrDetList = refundServiceImp.showDetCodeList("rfr");
                OrderDto order = orderServiceImp.selectOne(refund.getOrderId());
                model.addAttribute("refund", refund);
                model.addAttribute("rfDetList", rfDetList);
                model.addAttribute("rfrDetList", rfrDetList);
                model.addAttribute("order", order);
                return "refund/user/detail";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 아래 부분은 추후 삭제될 부분.
        List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
        List<CommonCodeDto> rfrDetList = refundServiceImp.showDetCodeList("rfr");
        OrderDto order = orderServiceImp.selectOne(refund.getOrderId());
        model.addAttribute("refund", refund);
        model.addAttribute("rfDetList", rfDetList);
        model.addAttribute("rfrDetList", rfrDetList);
        model.addAttribute("order", order);
        model.addAttribute("refundMsg", refundMsg);
        return "refund/user/detail";
    }

    @PostMapping("user_yes/mypage/modify.do/{refundId}")
    public String modify(@PathVariable int refundId,
                         @SessionAttribute UserDto loginUser,
                         HttpSession session) {
        int modify = 0;
        RefundDto upRefund = refundServiceImp.selectOne(refundId);
        if (loginUser.getUserId() == upRefund.getUserId()) {
            try {
                modify += refundServiceImp.modifyOne(upRefund, "user");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (modify > 0) {
            session.setAttribute("refundMsg", "환불 요청이 성공적으로 취소되었습니다.");
        } else {
            session.setAttribute("refundMsg", "정보가 일치하지 않습니다.");
        }
        return "redirect:/refund/user_yes/mypage/list.do";
    }

    @GetMapping("admin/list.do")
    public String adminList(@SessionAttribute UserDto loginUser,
                            Model model){
        List<String> rfDetList = null;
        List<RefundDto> refundList = refundServiceImp.showRefundList(rfDetList);
        model.addAttribute("refundList", refundList);
        return "refund/admin/list";
    }

}
