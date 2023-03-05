package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {
    private ReplyService replyService;
    private Logger log= LoggerFactory.getLogger(this.getClass());

    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    @GetMapping("/{commId}/list.do")
    public String list(@PathVariable int commId,
                       PagingDto paging,
                       HttpServletRequest req,
                       Model model){
        paging.setQueryString(req.getParameterMap());
        List<ReplyDto> replyList = replyService.commDetailList(commId,paging);
        model.addAttribute("replyList",replyList);
        model.addAttribute("paging",paging);
        return "/community/reply/list";
    }

    @PostMapping("/register.do")
    public @ResponseBody int register(ReplyDto reply,
                                      @SessionAttribute UserDto loginUser){
        int register=0;
        register=replyService.registerOne(reply);
        return register;
    }


}
