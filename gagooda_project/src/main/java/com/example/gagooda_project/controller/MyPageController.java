package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.*;
import com.example.gagooda_project.mapper.CartMapper;
import com.example.gagooda_project.mapper.CommonCodeMapper;
import com.example.gagooda_project.mapper.OrderDetailMapper;
import com.example.gagooda_project.service.CartService;
import com.example.gagooda_project.service.CartServiceImp;
import com.example.gagooda_project.service.MyPageService;
import com.example.gagooda_project.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    CartService cartService;
    MyPageService myPageService;

    public MyPageController(MyPageService myPageService, CartService cartService) {
        this.myPageService = myPageService;
        this.cartService = cartService;
    }

    @GetMapping("/user_yes/main.do")
    public String myPageMain(@SessionAttribute UserDto loginUser,
                             Model model,
                             HttpServletRequest req) {
        try {
            List<ODetDto> oDetList = myPageService.countByUserIdAndStatus(loginUser.getUserId());
            List<CommonCodeDto> myPageDetList = myPageService.showDetCodeList("o");
            PagingDto paging = new PagingDto();
            List<OrderDto> orderList = myPageService.orderList(paging, loginUser.getUserId(), 30);
            myPageDetList = myPageDetList.subList(0, 4);
            PagingDto paging2 = new PagingDto();
            List<CartDto> cartList = myPageService.cartList(paging2, loginUser.getUserId());
            Map<String, String> detName = new HashMap<>();
            Map<String, Integer> count = new HashMap<>();
            for (CommonCodeDto myPageDet : myPageDetList) {
                count.put(myPageDet.getDetCode(), 0);
                detName.put(myPageDet.getDetCode(), myPageDet.getDetName());
//                for (ODetDto oDetDto : oDetList) {
//                    if (oDetDto.getODet().equals(myPageDet.getDetCode())) {
//                        count.put(myPageDet.getDetCode(), oDetDto.getCount());
//                    }
//                }
            }
            for (ODetDto oDet : oDetList) {
                count.put(oDet.getODet(), oDet.getCount());
            }
            model.addAttribute("paging",paging);
            model.addAttribute("orderList",orderList);
            model.addAttribute("detName",detName);
            model.addAttribute("count",count);
            model.addAttribute("cartList", cartList);
            model.addAttribute("oDetList", oDetList);
            model.addAttribute("myPageDetList",myPageDetList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mypage/main";
        // commonCode 랑 o_det 가 같은 ODetDto를 oDetList에서 찾아오기
        // 있으면은 hashmap 없으면 hashmap에 put을 하는데, 개수가 0으로 들어오게
        /*
        {o0=결제 미확인, o1=결제 확인, o2=주문 완료, o3=구매 확정, o4=주문 취소, o5=주문 취소 요청}
        [ODetDto(oDet=o0, count=1), ODetDto(oDet=o1, count=1), ODetDto(oDet=o2, count=1), ODetDto(oDet=o3, count=1), ODetDto(oDet=o4, count=1)]
        {o0=결제 미확인, o1=결제 확인, o2=주문 완료, o3=구매 확정, o4=주문 취소, o5=주문 취소 요청}
        {o0=0, o1=0, o2=0, o3=0, o4=0, o5=0}
        {o0=1, o1=1, o2=1, o3=1, o4=1, o5=0}
        {o0=1, o1=1, o2=1, o3=1, o4=1}
         */
    }
}
