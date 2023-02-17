package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.MyPageService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("user_yes/main.do")
    public String myPageMain(@SessionAttribute UserDto loginUser) {
        return "mypage/main";
    }
}
