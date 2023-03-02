package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ZzimService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/zzim/")
public class ZzimController {
    private ZzimService zzimService;
    public ZzimController(ZzimService zzimService) {
        this.zzimService = zzimService;
    }

    @GetMapping("/list.do")
    public String zzimList(@SessionAttribute UserDto loginUser,
                           String productCode,
                           Model model) {

        return "";
    }


}
