package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
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
    public String signup(
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            session.removeAttribute("errMsg");
            System.out.println(errMsg);
        }
        return "/user/signup";
    }

    @PostMapping("/signup.do")
    public String signup(
            UserDto user,
            HttpSession session
    ) {
        int signup = 0;
        try {
            user.setGDet("g0");
            signup = userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user);
        if (signup > 0) {
            return "redirect:/user/login.do";
        } else {
            session.setAttribute("errMsg", "회원가입 중 오류가 있었습니다.");
            return "redirect:/user/signup.do";
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
            session.removeAttribute("redirectUri");
            return (redirectUri != null) ? "redirect:" + redirectUri : "redirect:/";
        }
    }

    @GetMapping("/findpw.do")
    public String findPw(
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            System.out.println(errMsg);
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

    @GetMapping("/{userId}/password_reset.do")
    public String resetPw(
            Model model,
            @PathVariable(required = true, name = "userId") int userId,
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            System.out.println(errMsg);
            session.removeAttribute("errMsg");
        }
        System.out.println("userId: " + userId);
        model.addAttribute("userId", userId);
        return "/user/password_reset";
    }

    @PostMapping("/password_reset.do")
    public String resetPw(
            String pw,
            int userId,
            HttpSession session
    ) {
        int reset = 0;
        try {
            UserDto user = userService.selectOne(userId);
            user.setPw(pw);
            reset = userService.modifyOne(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (reset > 0) {
            return "redirect:/user/login.do";
        } else {
            session.setAttribute("errMsg", "비밀번호 재설정에 실패했습니다.");
            return "redirect:/user/" + userId + "/password_reset.do";
        }
    }

    @GetMapping("/double_check.do")
    public String doubleCheck(
            @SessionAttribute(required = true) UserDto loginUser,
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            session.removeAttribute(errMsg);
            System.out.println(errMsg);
        }
        return "/user/double_check";
    }

    @PostMapping("/double_check.do")
    public String doubleCheck(
            @SessionAttribute(required = true) UserDto loginUser,
            String pw
    ) {
        UserDto user = null;
        try {
            user = userService.doubleCheck(loginUser.getEmail(), pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            return "redirect:/user/temp.do";
        } else {
            return "redirect:/user/double_check.do";
        }
    }

    /*임시적인 경로*/
    @GetMapping("/temp.do")
    public String temp(
            @SessionAttribute(required = true) UserDto loginUser,
            @SessionAttribute(required = false) String errMsg,
            HttpSession session
    ) {
        if (errMsg != null) {
            session.removeAttribute("errMsg");
            System.out.println(errMsg);
        }
        return "/user/temp";
    }

    @GetMapping("/{userId}/remove.do")
    public String remove(
            @SessionAttribute(required = true) UserDto loginUser,
            HttpSession session
    ) {
        int delete = 0;
        try {
            delete = userService.delete(loginUser.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (delete > 0) {
            session.removeAttribute("loginUser");
            return "redirect:/";
        } else {
            session.setAttribute("errMsg", "삭제 중 오류가 생겼습니다. 다시 시도해 주세요.");
            return "redirect:/user/temp.do";
        }
    }

    @GetMapping("/{userId}/modify.do")
    public String modify(
            @SessionAttribute(required = true) UserDto loginUser,
            HttpSession session
    ) {
        int modify = 0;
        try {
            System.out.println("modify");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }
}
