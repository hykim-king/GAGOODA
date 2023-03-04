package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.CartDto;
import com.example.gagooda_project.dto.ProductDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.dto.ZzimDto;
import com.example.gagooda_project.service.ProductService;
import com.example.gagooda_project.service.ZzimService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zzim/")
public class ZzimController {
    private ZzimService zzimService;
    private ProductService productService;
    public ZzimController(ZzimService zzimService,
                          ProductService productService) {
        this.zzimService = zzimService;
        this.productService = productService;
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

    @GetMapping("/user_yes/show.do")
    public String show (@SessionAttribute UserDto loginUser,
                        @RequestParam String productCode,
                        Model model) {
        try {
            ProductDto product = productService.selectOne(productCode);
            ZzimDto zzimDto = zzimService.selectOne(productCode,loginUser);
            Map<String, ZzimDto> zzim = new HashMap<>();
            zzim.put(productCode,zzimDto);
            model.addAttribute("product",product);
            model.addAttribute("zzim",zzim);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg","찜 안보임");
        }
        return "/zzim/register";
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
    @PostMapping("/user_yes/mypage/deleteOne.do")
    public String deleteOne(@SessionAttribute UserDto loginUser,
                            @RequestParam List<Integer> zzimIds,
                            HttpSession session) {
        int delete =0;
        try {
            for (int i=0; i<zzimIds.size();i++) {
                int id = Integer.parseInt(String.valueOf(zzimIds.get(i)));
                delete = zzimService.remove(id);
            }
        }catch( Exception e){
            session.setAttribute("msg","삭제에 실패하였습니다.");
        }
        if (delete>0) {
            return "redirect:/zzim/user_yes/mypage/list.do";
        } else {
            session.setAttribute("msg","삭제 실패");
            return "redirect:/zzim/user_yes/mypage/list.do";
        }
    }
}