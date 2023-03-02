package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.PagedDataList;
import com.siot.IamportRestClient.response.Payment;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundServiceImp refundServiceImp;
    OrderServiceImp orderServiceImp;
    ImageServiceImp imageServiceImp;
    AddressServiceImp addressServiceImp;
    PaymentServiceImp paymentServiceImp;
    ExchangeService exchangeService;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")
    private String imgPath;

    private final IamportClient iamportClient;
    private static int imageCount = 1000;

    public RefundController(RefundServiceImp refundServiceImp,
                            OrderServiceImp orderServiceImp,
                            ImageServiceImp imageServiceImp,
                            AddressServiceImp addressServiceImp,
                            PaymentServiceImp paymentServiceImp,
                            ExchangeService exchangeService) {
        this.refundServiceImp = refundServiceImp;
        this.orderServiceImp = orderServiceImp;
        this.imageServiceImp = imageServiceImp;
        this.addressServiceImp = addressServiceImp;
        this.paymentServiceImp = paymentServiceImp;
        this.exchangeService = exchangeService;
        this.iamportClient = new IamportClient("5625884002542635", "V1vlsxRPO59Ty3xDEKlhf2W7gkv2dMU4Hld8EFXYvuDedHnF3kRWvX1gqt9DNsg6GQSmoz2D76iogdnU");
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
            List<RefundDto> refundList = refundServiceImp.showUserRefundList(loginUser.getUserId(), period, startDate, endDate, detCode, paging);
            int userRefundCount = refundServiceImp.showCountByUser(loginUser.getUserId(), period, startDate, endDate, detCode);
            model.addAttribute("refundList", refundList);
            model.addAttribute("userRefundCount", userRefundCount);
            model.addAttribute("paging", paging);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("refundMsg", refundMsg);
        return "refund/user/list";
    }

    @GetMapping("user_yes/mypage/{orderDetailId}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable int orderDetailId,
                           HttpSession session,
                           Model model) {
        String refundMsg = null;
        if (session.getAttribute("refundMsg") != null) {
            log.info("$$$$$$$$$$$$$$$$MSGMSGMSG$$$$$$$$$$$$$$$");
            refundMsg = session.getAttribute("refundMsg").toString();
            session.removeAttribute("refundMsg");
            model.addAttribute("refundMsg", refundMsg);
        }
        try{
            OrderDetailDto orderDetail = refundServiceImp.selectOrderDetailByid(orderDetailId);
            OrderDto order = orderServiceImp.selectOne(orderDetail.getOrderId());
            int refundCount = refundServiceImp.countByOrderId(order.getOrderId());
            List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
            AddressDto orderAddress = refundServiceImp.selectAddress(order.getAddressId());

            model.addAttribute("orderDetail", orderDetail);
            model.addAttribute("order", order);
            model.addAttribute("refundCount", refundCount);
            model.addAttribute("orderAddress", orderAddress);
            model.addAttribute("addressList", addressList);

        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/";
        }
        return "refund/user/register";

    }

    @PostMapping("user_yes/mypage/{orderDetailId}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @PathVariable int orderDetailId,
                           HttpSession session,
                           @RequestParam(required = false, name="imgFileList") List<MultipartFile> imgFileList,
                           @RequestParam(required = false) int cnt,
                           RefundDto refund) {
        int register = 0;
        String reType;
        String detailimgPath;
        String code;
        int seq = 1;
            try {
                OrderDetailDto orderDetail = refundServiceImp.selectOrderDetailByid(orderDetailId);
                if (loginUser.getUserId() == refund.getUserId() && refund.getOrderId().equals(orderDetail.getOrderId())) {
                    /* 환불, 교환 체크하여 이름 지정 */
                    if (refund.isReType()) {
                        reType = "refund";
                        detailimgPath = imgPath + "/refund";
                        code = reType + refund.getOrderId() + refund.getOrderDetailId();
                        List<ImageDto> imgList = imageServiceImp.showImagesWithCode(code);
                        if(imgList != null){
                            code = code  + imageCount;
                            imageCount++;
                        }
                        refund.setImgCode(code);
                        register += refundServiceImp.registerOne(refund);
                        /* 등록 */
                        if (register > 0) { // 성공했을 시
                            if (imgFileList.size() > 0){
                                for (MultipartFile imgFile : imgFileList) {
                                    if (!imgFile.isEmpty()){
                                        register += imageServiceImp.registerMultipartImage(imgFile, detailimgPath, code, seq);
                                        seq++;
                                    }
                                }
                            }
                            seq = 0;
                            session.setAttribute("refundMsg", reType + " 요청에 성공했습니다.");
                            return "redirect:/refund/user_yes/mypage/" + refund.getRefundId() + "/detail.do";
                        } else { // 실패했을 시
                            session.setAttribute("refundMsg", reType + " 요청에 실패했습니다.");
                            return "redirect:/refund/user_yes/mypage/" + orderDetailId + "/register.do";
                        }
                    } else {
                        reType = "exchange";
                        detailimgPath = imgPath + "/exchange";
                        ExchangeDto exchange = new ExchangeDto();
                        exchange.setUserId(refund.getUserId());
                        exchange.setUname(refund.getUname());
                        exchange.setEmail(refund.getEmail());
                        exchange.setPhone(refund.getPhone());
                        exchange.setOrderId(refund.getOrderId());
                        exchange.setOrderDetailId(refund.getOrderDetailId());
                        exchange.setAddressId(refund.getAddressId());
                        exchange.setCnt(cnt);
                        exchange.setReason(refund.getReason());
                        exchange.setPostCode(refund.getPostCode());
                        exchange.setAddress(refund.getAddress());
                        exchange.setAddressDetail(refund.getAddressDetail());
                        exchange.setReceiverName(refund.getReceiverName());
                        exchange.setReceiverPhone(refund.getReceiverPhone());
                        exchange.setElevator(refund.isElevator());
                        exchange.setRfrDet(refund.getRfrDet());
                        /* 등록 */
                        code = reType + exchange.getOrderId() + exchange.getOrderDetailId();
                        List<ImageDto> imgList = imageServiceImp.showImagesWithCode(code);
                        if(imgList != null){
                            code = code  + imageCount;
                            imageCount++;
                        }
                        exchange.setImgCode(code);
                        register += exchangeService.registerOne(exchange);
                        if (register > 0) { // 성공했을 시
                            if (imgFileList.size() > 0) {
                                for (MultipartFile imgFile : imgFileList) {
                                    if (!imgFile.isEmpty()){
                                        imageServiceImp.registerMultipartImage(imgFile, detailimgPath, code, seq);
                                        seq++;
                                    }
                                }
                            }
                            seq = 0;
                            session.setAttribute("refundMsg", reType + " 요청에 성공했습니다.");
                            return "redirect:/exchange/user_yes/mypage/" + exchange.getExchangeId() + "/detail.do";
                        } else { // 실패했을 시
                            session.setAttribute("refundMsg", reType + " 요청에 실패했습니다.");
                            return "redirect:/exchange/user_yes/mypage/" + orderDetailId + "/register.do";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return "redirect:/refund/user_yes/mypage/"+orderDetailId+"/register.do";
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
    @GetMapping("user_yes/mypage/{orderDetailId}/addressList.do")
    public String addressList(Model model,
                              HttpSession session,
                              @PathVariable int orderDetailId,
                              @SessionAttribute UserDto loginUser){
        String refundMsg = "";
        int addressId = 0;
        try{
            if (session.getAttribute("refundMsg") != null ){
                refundMsg = session.getAttribute("refundMsg").toString();
                session.removeAttribute("refundMsg");
                model.addAttribute("refundMsg",refundMsg);
            }
            if (session.getAttribute("addressId") != null ){
                addressId = (int) session.getAttribute("addressId");
                session.removeAttribute("addressId");
                AddressDto address = addressServiceImp.selectOne(addressId);
                model.addAttribute("addressId", addressId);
                model.addAttribute("address",address);
            }
            OrderDetailDto orderDetail = refundServiceImp.selectOrderDetailByid(orderDetailId);
            OrderDto order = orderServiceImp.selectOne(orderDetail.getOrderId());
            if(loginUser.getUserId() == order.getUserId()){
                List<AddressDto> addressList = refundServiceImp.showAddressListByUserId(loginUser.getUserId());
                model.addAttribute("addressList",addressList);
                model.addAttribute("order",order);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "refund/user/newAddress";
    }
    @PostMapping("user_yes/mypage/{orderDetailId}/addressRegister.do")
    public @ResponseBody int addressRegister(@SessionAttribute UserDto loginUser,
                               @PathVariable int orderDetailId,
                               AddressDto address,
                               HttpSession session ){
        int register = 0;
        int addressId;
        try{
            register = addressServiceImp.register(address);
            if (register > 0){
                addressId = address.getAddressId();
                session.setAttribute("refundMsg", "new");
                session.setAttribute("addressId", addressId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return register;
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
        log.info("requestUri:" +req.getRequestURI());
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
                              HttpSession session,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser,
                              Model model){
        try{
            if(loginUser.getGDet().equals("g1")){
                RefundDto refund = refundServiceImp.selectOne(refundId);
                OrderDto order = orderServiceImp.selectOne(refund.getOrderId());
                OrderDetailDto orderDetail = refundServiceImp.selectOrderDetailByid(refund.getOrderDetailId());
//                session.setAttribute("prevUri",req.getRequestURI());
                List<CommonCodeDto> allRfList = refundServiceImp.showDetCodeList("rf");
//                PaymentDto payment = paymentServiceImp.selectOne(refund.getOrderId());
//                IamportResponse<Payment> paymentResp = iamportClient.paymentByImpUid(payment.getImpUid());
                model.addAttribute("refund", refund);
                model.addAttribute("order",order);
                model.addAttribute("orderDetail", orderDetail);
                model.addAttribute("rfCodeList", allRfList);
//                model.addAttribute("payment", paymentResp.getResponse());
                return "refund/admin/detail";
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return "redirect:/refund/admin/list.do";
//        return "refund/admin/detail";
    }

    @PostMapping("admin/{refundId}/modify.do")
    public String adminModify(@PathVariable int refundId,
                              RefundDto refund,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser){
        int modify = 0;
        if(loginUser.getGDet().equals("g1") && refundId == refund.getRefundId()){
            try{
                modify += refundServiceImp.modifyOne(refund, "admin");
                if(modify > 0){
                    return "redirect:/refund/admin/detail.do";
                }
            }catch (Exception e){
                e.printStackTrace();
                return "redirect:"+req.getRequestURI();
            }
        }
        return "redirect:"+req.getRequestURI();
    }

    //임시 결제 페이지
    @GetMapping("user_yes/{orderId}/payments/temp.do")
    public String paymentsTemp(@PathVariable String orderId,
                               Model model){
        try{
            OrderDto order = orderServiceImp.selectOne(orderId);
            IamportResponse<Payment> paymentResp = iamportClient.paymentByImpUid("imp_969193682313");
            IamportResponse<PagedDataList<Payment>> allPaymentsResp = iamportClient.paymentsByStatus("all");
            model.addAttribute("order", order);
            model.addAttribute("payment", paymentResp.getResponse());
            model.addAttribute("allPayments", allPaymentsResp.getResponse());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "refund/user/paymentTemp";
    }
    //결제 취소 GET
    @GetMapping("admin/payments/cancel.do")
    public String cancelPaymentByIamUid(@SessionAttribute UserDto loginUser){
        if(loginUser.getGDet().equals("g1")){
            return "refund/admin/cancelOrder";
        }else{
            return "mainpage";
        }
    }
    //결제 취소 POST
    @PostMapping("admin/payments/cancel.do")
    @ResponseBody
    public IamportResponse<Payment> cancelPaymentByIamUid(String orderId,
                                        Integer cancelAmount,
                                        String reason,
                                        HttpSession session,
                                        HttpServletRequest req,
                                        @SessionAttribute UserDto loginUser){
        IamportResponse<Payment> cancelResp = null;
        int code = 1;
        try{
            if (loginUser.getGDet().equals("g1")){
                if(reason == null){
                    reason = "주문 취소";
                }
                CancelData cancelData = new CancelData(orderId, false, BigDecimal.valueOf(cancelAmount) );
                cancelData.setReason(reason);
                log.info("orderId: "+orderId);
                log.info("cancelAmount: "+cancelAmount);
                log.info("reason: "+reason);
                log.info("cancelData: " + cancelData);
                cancelResp = iamportClient.cancelPaymentByImpUid(cancelData);
                log.info("cancelResp: "+ cancelResp);
                log.info("cancelResp.getResponse: "+ cancelResp.getResponse());
                log.info("cancelResp.getMessage: "+cancelResp.getMessage());
                log.info("cancelResp.getCode: "+cancelResp.getCode());
//                session.setAttribute("msg",cancelResp.getMessage());
                code = cancelResp.getCode();
                if (code == 0){
                    return cancelResp;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cancelResp;
    }

    // 결제 조회 POST --> impUid 저장되어 있으면 어디서든 해당 건에 대한 결제 정보를 조회할 수 있으며, 영수증 url도 포함하고 있다.
    @PostMapping("/admin/payments/find.do")
    @ResponseBody
    public Payment paymentByImpUid(String impUid,
                                   @SessionAttribute UserDto loginUser){
        try{
            if(loginUser.getGDet().equals("g1")){
                IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
                return paymentResponse.getResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
