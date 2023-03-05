package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.service.*;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.PagedDataList;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/refund")
public class RefundController {

    RefundService refundService;
    OrderService orderService;
    ImageService imageService;
    AddressService addressService;
    PaymentService paymentService;
    ExchangeService exchangeService;
    Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Value("${img.upload.path}")
    private String imgPath;

    private final IamportClient iamportClient;
    private static int imageCount = 1000;

    public RefundController(RefundService refundService,
                            OrderService orderService,
                            ImageService imageService,
                            AddressService addressService,
                            PaymentService paymentService,
                            ExchangeService exchangeService) {
        this.refundService = refundService;
        this.orderService = orderService;
        this.imageService = imageService;
        this.addressService = addressService;
        this.paymentService = paymentService;
        this.exchangeService = exchangeService;
        this.iamportClient = new IamportClient("5625884002542635", "V1vlsxRPO59Ty3xDEKlhf2W7gkv2dMU4Hld8EFXYvuDedHnF3kRWvX1gqt9DNsg6GQSmoz2D76iogdnU");
    }

    @GetMapping("user_yes/mypage/list.do")
    public String list(@SessionAttribute UserDto loginUser,
                       @SessionAttribute(required = false) String msg,
                       @RequestParam(name = "period", defaultValue = "7", required = false) int period,
                       @RequestParam(name = "startDate", required = false) String startDate,
                       @RequestParam(name = "endDate", required = false) String endDate,
                       @RequestParam(name = "detCode", required = false) String detCode,
                       PagingDto paging,
                       HttpServletRequest req,
                       HttpSession session,
                       Model model) {
        log.info("req.getParameterMap:"+req.getParameterMap());
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try {
            paging.setQueryString(req.getParameterMap());
            List<RefundDto> refundList = refundService.showUserRefundList(loginUser.getUserId(), period, startDate, endDate, detCode, paging);
            int userRefundCount = refundService.showCountByUser(loginUser.getUserId(), period, startDate, endDate, detCode);
            model.addAttribute("refundList", refundList);
            model.addAttribute("userRefundCount", userRefundCount);
            model.addAttribute("paging", paging);
            return "refund/user/list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }

    @GetMapping("user_yes/mypage/{orderDetailId}/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @SessionAttribute(required = false) String msg,
                           @PathVariable int orderDetailId,
                           HttpSession session,
                           Model model) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try{
            OrderDetailDto orderDetail = refundService.selectOrderDetailByid(orderDetailId);
            OrderDto order = orderService.selectOne(orderDetail.getOrderId());
            if(loginUser.getUserId() == order.getUserId()){
                List<RefundDto> checkRefundList = refundService.selectOrderDetail(orderDetail.getOrderDetailId());
                List<ExchangeDto> checkExchangeList = exchangeService.selectOrderDetail(orderDetail.getOrderDetailId());
                boolean isOk = true;
                if(checkRefundList != null){
                    for(RefundDto checkRefund : checkRefundList){
                        if(!checkRefund.getRfDet().equals("rf1")){
                            isOk = false;
                            break;
                        }
                    }
                }
                if(checkExchangeList != null){
                    for(ExchangeDto checkExchange : checkExchangeList){
                        if(!checkExchange.getExDet().equals("ex1")){
                            isOk = false;
                            break;
                        }
                    }
                }
                if(isOk){
                    int refundCount = refundService.countByOrderId(order.getOrderId());
                    List<AddressDto> addressList = refundService.showAddressListByUserId(loginUser.getUserId());
                    AddressDto orderAddress = refundService.selectAddress(order.getAddressId());

                    model.addAttribute("orderDetail", orderDetail);
                    model.addAttribute("order", order);
                    model.addAttribute("refundCount", refundCount);
                    model.addAttribute("orderAddress", orderAddress);
                    model.addAttribute("addressList", addressList);
                    return "refund/user/register";
                }else{
                    session.setAttribute("msg", "이미 교환/환불이 신청된 상품입니다.");
                    return "redirect:/order/user_yes/mypage/"+order.getOrderId()+"/detail.do";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
        return "redirect:/";
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
                OrderDetailDto orderDetail = refundService.selectOrderDetailByid(orderDetailId);
                List<RefundDto> checkRefund = refundService.selectOrderDetail(orderDetail.getOrderDetailId());
                List<ExchangeDto> checkExchange = exchangeService.selectOrderDetail(orderDetail.getOrderDetailId());

                if (loginUser.getUserId() == refund.getUserId() && refund.getOrderId().equals(orderDetail.getOrderId())) {
                    /* 환불, 교환 체크하여 이름 지정 */
                    if (refund.isReType()) {
                        reType = "refund";
                        detailimgPath = imgPath + "/refund";
                        code = reType + refund.getOrderId() + refund.getOrderDetailId();
                        List<ImageDto> imgList = imageService.showImagesWithCode(code);
                        if(imgList != null){
                            code = code  + imageCount;
                            imageCount++;
                        }
                        refund.setImgCode(code);
                        register += refundService.registerOne(refund);
                        /* 등록 */
                        if (register > 0) { // 성공했을 시
                            if (imgFileList.size() > 0){
                                for (MultipartFile imgFile : imgFileList) {
                                    if (!imgFile.isEmpty()){
                                        register += imageService.registerMultipartImage(imgFile, detailimgPath, code, seq);
                                        seq++;
                                    }
                                }
                            }
                            seq = 0;
                            session.setAttribute("msg", "환불 요청에 성공했습니다.");
                            return "redirect:/refund/user_yes/mypage/" + refund.getRefundId() + "/detail.do";
                        } else { // 실패했을 시
                            session.setAttribute("msg", "환불 요청에 실패했습니다.");
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
                        List<ImageDto> imgList = imageService.showImagesWithCode(code);
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
                                        imageService.registerMultipartImage(imgFile, detailimgPath, code, seq);
                                        seq++;
                                    }
                                }
                            }
                            seq = 0;
                            session.setAttribute("msg", "교환 요청에 성공했습니다.");
                            return "redirect:/exchange/user_yes/mypage/" + exchange.getExchangeId() + "/detail.do";
                        } else { // 실패했을 시
                            session.setAttribute("msg", "교환 요청에 실패했습니다.");
                            return "redirect:/exchange/user_yes/mypage/" + orderDetailId + "/register.do";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
                return "redirect:/";
            }
        session.setAttribute("msg", "잘못된 접근입니다.");
        return "redirect:/";
    }

    @GetMapping("user_yes/mypage/{refundId}/detail.do")
    public String detail(@SessionAttribute UserDto loginUser,
                         @SessionAttribute(required = false) String msg,
                         @PathVariable int refundId,
                         HttpSession session,
                         Model model) {
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        RefundDto refund = null;
        try {
            refund = refundService.selectOne(refundId);
            if (loginUser.getUserId() == refund.getUserId()) {
                List<CommonCodeDto> rfDetList = refundService.showDetCodeList("rf");
                List<CommonCodeDto> rfrDetList = refundService.showDetCodeList("rfr");
                OrderDto order = orderService.selectOne(refund.getOrderId());
                if(refund.getRfDet().equals("rf7")){
                    PaymentDto paymentDto = paymentService.selectOne(refund.getOrderId());
                    IamportResponse<Payment> paymentResp = iamportClient.paymentByImpUid(paymentDto.getImpUid());
                    if(paymentResp != null){
                        model.addAttribute("payment", paymentResp.getResponse());
                    }
                }
                model.addAttribute("refund", refund);
                model.addAttribute("rfDetList", rfDetList);
                model.addAttribute("rfrDetList", rfrDetList);
                model.addAttribute("order", order);
                return "refund/user/detail";
            }else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "refund/user/list";
        }
    }

    @PostMapping("user_yes/mypage/{refundId}/modify.do")
    public String modify(@PathVariable int refundId,
                         @SessionAttribute UserDto loginUser,
                         HttpSession session) {
        int modify = 0;
        RefundDto upRefund = refundService.selectOne(refundId);
        if (loginUser.getUserId() == upRefund.getUserId()) {
            try {
                modify += refundService.modifyOne(upRefund, "user");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
                return "redirect:/";
            }
        }
        if (modify > 0) {
            session.setAttribute("msg", "환불 요청이 성공적으로 취소되었습니다.");
            return "redirect:/refund/user_yes/mypage/list.do";
        } else {
            session.setAttribute("msg", "정보가 일치하지 않습니다.");
            return "redirect:/refund/user_yes/mypage/"+refundId+"/detail.do";
        }
    }
    @GetMapping("user_yes/mypage/{orderDetailId}/addressList.do")
    public String addressList(Model model,
                              HttpSession session,
                              @PathVariable int orderDetailId,
                              @SessionAttribute(required = false) String msg,
                              @SessionAttribute UserDto loginUser){
        int addressId = 0;
        try{
            if (session.getAttribute("addressId") != null ){
                addressId = (int) session.getAttribute("addressId");
                session.removeAttribute("addressId");
                AddressDto address = addressService.selectOne(addressId);
                model.addAttribute("addressId", addressId);
                model.addAttribute("address",address);
            }
            OrderDetailDto orderDetail = refundService.selectOrderDetailByid(orderDetailId);
            OrderDto order = orderService.selectOne(orderDetail.getOrderId());
            if(loginUser.getUserId() == order.getUserId()){
                List<AddressDto> addressList = refundService.showAddressListByUserId(loginUser.getUserId());
                model.addAttribute("addressList",addressList);
                model.addAttribute("order",order);
            }
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
        return "refund/user/newAddress";
    }
    @PostMapping("user_yes/mypage/{orderDetailId}/addressRegister.do")
    @ResponseBody
    public AddressDto addressRegister(@SessionAttribute UserDto loginUser,
                               @PathVariable int orderDetailId,
                               AddressDto address,
                               HttpSession session ){
        int register = 0;
        log.info("$$$$$$$$$$$$$$$"+address.getAddress());
        log.info("$$$$$$$$$$$$$$$"+address.getAddressDetail());
        log.info("$$$$$$$$$$$$$$$"+address.getPostCode());
        log.info("$$$$$$$$$$$$$$$"+address.getUserId());
        try{
            OrderDetailDto orderDetail = refundService.selectOrderDetailByid(orderDetailId);
            OrderDto order = orderService.selectOne(orderDetail.getOrderId());
            if (loginUser.getUserId() == order.getUserId()){
                register = addressService.register(address);
                if (register > 0){
                    return refundService.selectAddress(address.getAddressId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // 관리자 리스트 페이지
    @GetMapping("admin/list.do")
    public String adminList(@SessionAttribute UserDto loginUser,
                            @SessionAttribute(required = false) String msg,
                            HttpSession session,
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
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        try{
            if (loginUser.getGDet().equals("g1")){
                Map<String, Object> searchFilter = new HashMap<>();
                paging.setQueryString(req.getParameterMap());
                searchFilter.put("rfDet",rfDet); searchFilter.put("searchDiv",searchDiv); searchFilter.put("searchWord", searchWord);
                searchFilter.put("dateType", dateType); searchFilter.put("startDate", startDate); searchFilter.put("endDate", endDate);
                searchFilter.put("paging", paging);
                List<CommonCodeDto> rfCodeList = refundService.showDetCodeList("rf");
                List<RefundDto> refundList = refundService.showRefundList(searchFilter);
                int refundCount = refundService.countPageAll(searchFilter);
                int allRfCnt = refundService.countAll();
                model.addAttribute("refundList", refundList);
                model.addAttribute("rfCodeList", rfCodeList);
                model.addAttribute("paging",paging);
                model.addAttribute("refundCount", refundCount);
                model.addAttribute("allCount",allRfCnt);
                return "refund/admin/list";
            }
            else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        }catch (Exception e){
            e.printStackTrace();
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }
    // 관리자 상세 페이지
    @GetMapping("admin/{refundId}/detail.do")
    public String adminDetail(@PathVariable int refundId,
                              @SessionAttribute(required = false) String msg,
                              HttpSession session,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser,
                              Model model){
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        PaymentDto payment = null;
        IamportResponse<Payment> paymentResp = null;
        try{
            if(loginUser.getGDet().equals("g1")){
                RefundDto refund = refundService.selectOne(refundId);
                OrderDto order = orderService.selectOne(refund.getOrderId());
                OrderDetailDto orderDetail = refundService.selectOrderDetailByid(refund.getOrderDetailId());
                List<CommonCodeDto> allRfList = refundService.showDetCodeList("rf");

                payment = paymentService.selectOne(refund.getOrderId());
                if (payment != null){
                    paymentResp = iamportClient.paymentByImpUid(payment.getImpUid());
                    if (paymentResp != null){
                       model.addAttribute("payment", paymentResp.getResponse());
                    }
                }
                model.addAttribute("refund", refund);
                model.addAttribute("order",order);
                model.addAttribute("orderDetail", orderDetail);
                model.addAttribute("rfCodeList", allRfList);

                return "refund/admin/detail";
            }else{
                session.setAttribute("msg", "잘못된 접근입니다.");
                return "redirect:/";
            }
        }catch (Exception e){
            log.info(e.getMessage());
            session.setAttribute("msg", "데이터를 가져오는 데에 문제가 있었습니다");
            return "redirect:/";
        }
    }
    // 관리자 상태, 답변 변경
    @PostMapping("admin/{refundId}/modify.do")
    public String adminModify(@PathVariable int refundId,
                              HttpSession session,
                              RefundDto refund,
                              HttpServletRequest req,
                              @SessionAttribute UserDto loginUser){
        int modify = 0;
        if(loginUser.getGDet().equals("g1") && refundId == refund.getRefundId()){
            try{
                modify += refundService.modifyOne(refund, "admin");
                if(modify > 0){
                    return "redirect:/refund/admin/"+refundId+"/detail.do";
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:"+req.getRequestURI();
    }

    //임시 결제 페이지
    @GetMapping("user_yes/{orderId}/payments/temp.do")
    public String paymentsTemp(@PathVariable String orderId,
                               Model model){
        try{
            OrderDto order = orderService.selectOne(orderId);
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
    public String cancelPaymentByIamUid(@SessionAttribute UserDto loginUser,
                                        HttpSession session){
        if(loginUser.getGDet().equals("g1")){
            return "refund/admin/cancelOrder";
        }else{
            session.setAttribute("msg", "잘못된 접근입니다.");
            return "redirect:/";
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
