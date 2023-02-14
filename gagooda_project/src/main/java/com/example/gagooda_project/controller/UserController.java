package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;

@Controller
@RequestMapping("/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup.do")
    public String signup() {
        return "/user/signup";
    }

    @PostMapping("/signup.do")
    public String signup(UserDto user) {
        int signup = 0;
        try {
            signup = userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user);
        if (signup > 0) {
            return "redirect:/user/login.do";
        } else {
            return "redirect:/user/register.do";
        }
    }

    @GetMapping("/login.do")
    public String login(@SessionAttribute(required = false) String errMsg,
                        HttpSession session) {
        session.removeAttribute("errMsg");
        System.out.println(errMsg);
        return "/user/login";
    }

    @PostMapping("/login.do")
    public String login(
            HttpSession session,
            String email,
            String pw,
            @SessionAttribute(required = false) String redirectUri
    ) {
        UserDto user = null;
        try {
            user = userService.login(email, pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            session.setAttribute("errMsg", "이메일 주소나 비밀번호가 잘못되었습니다.");
            return "redirect:/user/login.do";
        } else {
            session.setAttribute("loginUser", user);
            return (redirectUri != null) ? "redirect:" + redirectUri : "redirect:/";
        }
    }

    @GetMapping("/findpw.do")
    public String findPw(
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            session.removeAttribute("errMsg");
        }
        return "/user/find_pw";
    }

    @PostMapping("/findpw.do")
    public String findPw(
            String uname,
            String email,
            HttpSession session
    ) {
        UserDto user = null;
        try {
            user = userService.findpw(email, uname);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            session.setAttribute("errMsg", "존재하지 않는 이름이거나 이메일 주소 입니다");
            return "redirect:/user/findpw.do";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/password_reset.do/{code}")
    public String resetPw(
            Model model,
            @PathVariable(required = true) String code
    ) {
        model.addAttribute("code", code);
        return "/user/password_reset";
    }

    @PostMapping("/password_reset.do/{code}")
    public String resetPw(
            String pw,
            @PathVariable(required = true) String code
    ){
        char[] numbers = {'B','Z','E','G','Y','H','J','C','A','M'};
        System.out.println("code "+code.substring(20, code.length()-15));
        return "redirect:/";
    }

}
