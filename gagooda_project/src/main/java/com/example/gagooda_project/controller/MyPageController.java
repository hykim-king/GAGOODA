package com.example.gagooda_project.controller;

import com.example.gagooda_project.service.MyPageServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myPageController")
public class MyPageController {
    MyPageServiceImp myPageService;
}
