package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.ImageServiceImp;
import com.example.gagooda_project.service.OrderServiceImp;
import com.example.gagooda_project.service.RefundServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;
    OrderServiceImp orderServiceImp;
    ImageServiceImp imageServiceImp;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")
    private String imgPath;

    public RefundController(RefundServiceImp refundServiceImp, OrderServiceImp orderServiceImp,ImageServiceImp imageServiceImp) {
        this.refundServiceImp = refundServiceImp;
        this.orderServiceImp = orderServiceImp;
        this.imageServiceImp = imageServiceImp;
    }

    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       @RequestParam(name = "startDate", required = false) String startDate,
                       @RequestParam(name = "endDate", required = false) String endDate,
                       @RequestParam(name = "detCode", required = false) String detCode,
                       PagingDto paging,
                       HttpServletRequest req,
                       HttpSession session,
                       Model model) {
        log.info("req.getParameterMap:"+req.getParameterMap());
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        try {
            paging.setQueryString(req.getParameterMap());
            List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
            List<RefundDto> refundList = refundServiceImp.showUserRefundList(loginUser.getUserId(), period, startDate, endDate, detCode, paging);
            int userRefundCount = refundServiceImp.showCountByUser(loginUser.getUserId(), period, startDate, endDate, detCode);
            Map<String, Integer> orderDetailCountMap = new HashMap<>();
            for (RefundDto refund : refundList) {
                orderDetailCountMap.put(refund.getOrderId(), refundServiceImp.CountByOrderId(refund.getOrderId()));
            }
            model.addAttribute("refundList", refundList);
            model.addAttribute("rfDetList", rfDetList);
            model.addAttribute("userRefundCount", userRefundCount);
            model.addAttribute("orderDetailCountMap", orderDetailCountMap);
            model.addAttribute("paging", paging);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("refundMsg", refundMsg);
        return "refund/user/list";
    }

    @GetMapping("user_yes/mypage/{orderId}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable String orderId,
                           HttpSession session,
                           Model model) {
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        try{
            OrderDto order = orderServiceImp.selectOne(orderId);
            int refundCount = refundServiceImp.countByOrderId(orderId);
            List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
            AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());

            model.addAttribute("order", order);
            model.addAttribute("refundCount", refundCount);
            model.addAttribute("orderAddress", orderAddress);
            model.addAttribute("addressList", addressList);
            model.addAttribute("refundMsg", refundMsg);
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
        return "refund/user/register";

    }

    @PostMapping("user_yes/mypage/{orderId}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable String orderId,
                           HttpSession session,
                           @RequestParam(required = false, name="imgFileList") List<MultipartFile> imgFileList,
                           RefundDto refund) {
        int register = 0;
        String reType;
        String detailimgPath;
        int seq = 1;

        RefundDto checkDto;
        if (loginUser.getUserId() == refund.getUserId() && refund.getOrderId().equals(orderId)) {
            try {
                /* 환불, 교환 체크하여 이름 지정 */
                if (refund.isReType()){
                    reType = "refund";
                    detailimgPath = imgPath + "/refund";
                }else{
                    reType = "exchange";
                    detailimgPath = imgPath + "/exchange";
                }
                /* 등록 */
                if (refund.getOrderDetailId() == -1){ // 주문 상품 전체 등록
                    OrderDto order = orderServiceImp.selectOne(orderId);
                    List<RefundDto> checkList = null;
                    if(order.getOrderDetailList() !=null){ // 주문의 상세 주문이 null이 아니면
                        for(OrderDetailDto orderDetail : order.getOrderDetailList()){ // 주문 상세를 각각 조회해서
                            checkList = refundServiceImp.selectOrderDetail(orderDetail.getOrderDetailId()); // 해당 주문상세 번호로 등록된 refund 목록을 가져오고
                            if (checkList != null){  // 등록된 refund가 있으면
                                refund.setOrderDetailId(orderDetail.getOrderDetailId());
                                refund.setCancelAmount(orderDetail.getPrice());
                                register += refundServiceImp.registerOne(refund, imgFileList, detailimgPath, reType, seq);
                            }else{
                                refund.setOrderDetailId(orderDetail.getOrderDetailId());
                                register += refundServiceImp.registerOne(refund, imgFileList, detailimgPath, reType, seq);
                            }
                        }
                        session.setAttribute("refundMsg", reType + " 요청에 성공했습니다.");
                        return "redirect:/refund/user_yes/mypage/list.do";
                    }
                }else{ // 단건 등록
                    register += refundServiceImp.registerOne(refund, imgFileList, detailimgPath, reType, seq);
                }
                if (register > 0) { // 성공했을 시
                    session.setAttribute("refundMsg", reType + " 요청에 성공했습니다.");
                    return "redirect:/refund/user_yes/mypage/"+refund.getRefundId()+"/detail.do";
                } else { // 실패했을 시
                    session.setAttribute("refundMsg", reType + " 요청에 실패했습니다.");
                    return "redirect:/refund/user_yes/mypage/"+orderId+"/register.do" ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/refund/user_yes/mypage/"+orderId+"/register.do";
    }

    @GetMapping("user_yes/mypage/{refundId}/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @PathVariable int refundId,
                         HttpSession session,
                         Model model) {
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
        }
        RefundDto refund = null;
        try {
            refund = refundServiceImp.selectOne(refundId);
            if (loginUser.getUserId() == refund.getUserId()) {
                List<CommonCodeDto> rfDetList = refundServiceImp.showDetCodeList("rf");
                List<CommonCodeDto> rfrDetList = refundServiceImp.showDetCodeList("rfr");
                OrderDto order = orderServiceImp.selectOne(refund.getOrderId());
                model.addAttribute("refund", refund);
                model.addAttribute("rfDetList", rfDetList);
                model.addAttribute("rfrDetList", rfrDetList);
                model.addAttribute("order", order);
                model.addAttribute("refundMsg", refundMsg);
                return "refund/user/detail";
            }else{
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "refund/user/list";
    }

    @PostMapping("user_yes/mypage/{refundId}/modify.do")
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
            return "redirect:/refund/user_yes/mypage/list.do";
        } else {
            session.setAttribute("refundMsg", "정보가 일치하지 않습니다.");
            return "redirect:/refund/user_yes/mypage/detail.do/"+refundId;
        }
    }

    @GetMapping("admin/list.do")
    public String adminList(@SessionAttribute UserDto loginUser,
                            PagingDto paging,
                            HttpServletRequest req,
                            @RequestParam(name = "rfDet", required = false, defaultValue = "") String rfDet,
                            @RequestParam(name = "searchDiv", required = false, defaultValue = "") String searchDiv,
                            @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
                            @RequestParam(name = "dateType", required = false, defaultValue = "") String dateType,
                            @RequestParam(name = "startDate", required = false, defaultValue = "") String startDate,
                            @RequestParam(name = "endDate", required = false, defaultValue = "") String endDate,
                            Model model){
        log.info("req.getParameterMap:"+req.getParameterMap());
        try{
            if (loginUser.getGDet().equals("g1")){
                Map<String, Object> searchFilter = new HashMap<>();
                paging.setQueryString(req.getParameterMap());
                searchFilter.put("rfDet",rfDet); searchFilter.put("searchDiv",searchDiv); searchFilter.put("searchWord", searchWord);
                searchFilter.put("dateType", dateType); searchFilter.put("startDate", startDate); searchFilter.put("endDate", endDate);
                searchFilter.put("paging", paging);
                List<CommonCodeDto> rfCodeList = refundServiceImp.showDetCodeList("rf");
                List<RefundDto> refundList = refundServiceImp.showRefundList(searchFilter);
                int refundCount = refundServiceImp.countPageAll(searchFilter);
                int allRfCnt = refundServiceImp.countAll();
                log.info(refundList.toString()+"$$$$$$$$$$$$$$$$$$$$$");
                model.addAttribute("refundList", refundList);
                model.addAttribute("rfCodeList", rfCodeList);
                model.addAttribute("paging",paging);
                model.addAttribute("refundCount", refundCount);
                model.addAttribute("allCount",allRfCnt);
                return "refund/admin/list";
            }
            else{
                return "/index";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "refund/admin/list";
    }

    @GetMapping("admin/{refundId}/detail.do")
    public String adminDetail(@PathVariable int refundId,
                              @SessionAttribute UserDto loginUser,
                              Model model){
        RefundDto refund = refundServiceImp.selectOne(refundId);
        if(loginUser.getGDet().equals("g1")){
            List<CommonCodeDto> allRfList = refundServiceImp.showDetCodeList("rf");
            model.addAttribute("refund", refund);
            model.addAttribute("rfCodeList", allRfList);
            return "refund/admin/detail";
        }
        return "index";
    }

}
