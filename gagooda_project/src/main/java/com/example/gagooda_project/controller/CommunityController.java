package com.example.gagooda_project.controller;


import com.example.gagooda_project.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/community")
@Controller
public class CommunityController {

//    @PostMapping("/user_yes/register.do")
//    public String register(@SessionAttribute UserDto loginUser){
//        return null;
//    }
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser){
        return "/community/user/register";
    }
}
