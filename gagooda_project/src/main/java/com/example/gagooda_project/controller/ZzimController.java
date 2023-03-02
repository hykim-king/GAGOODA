package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.dto.ZzimDto;
import com.example.gagooda_project.service.ProductService;
import com.example.gagooda_project.service.ZzimService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/zzim/")
public class ZzimController {
    private ZzimService zzimService;
    public ZzimController(ZzimService zzimService) {
        this.zzimService = zzimService;
    }

    @GetMapping("/user_yes/mypage/list.do")
    public String zzimList(@SessionAttribute UserDto loginUser,
                           @SessionAttribute(required = false) String msg,
                           HttpSession session,
                           Model model) {
        if (msg!=null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try {
            List<ZzimDto> zzimList = zzimService.listByUserId(loginUser.getUserId());
            model.addAttribute("zzimList",zzimList);
            return "/zzim/list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("msg","찜 목록을 불러올수 없습니다.");
            return "redirect:/";
        }
    }

    @PostMapping("/user_yes/mypage/register.do")
    public @ResponseBody int register(@SessionAttribute UserDto loginUser,
                                  @SessionAttribute(required = false) String msg,
                                  HttpSession session,
                                  ZzimDto zzim,
                                  Model model) {
        int register = 0;
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg",msg);
        }
        try {
            register = zzimService.insert(zzim);
            return register;
        } catch (Exception e) {
            session.setAttribute("msg", "찜 등록에 실패했습니다.");
            return 0;
        }
    }

    @DeleteMapping("/user_yes/mypage/delete.do")
    public @ResponseBody int delete(@SessionAttribute UserDto loginUser,
                                    @RequestParam(name = "zzimId") int zzimId,
                                    HttpSession session) {
        try {
            int delete = zzimService.remove(zzimId);
            return delete;
        } catch (Exception e) {
            session.setAttribute("msg","찜 삭제 실패");
            return 0;
        }
    }
}
