package com.example.gagooda_project.controller;

import com.example.gagooda_project.dto.AjaxStateHandler;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
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
                       Model model,
                       HttpSession session,
                       @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        log.info("replylist를 부르기 위해 controller에 진입했습니다.");
        paging.setQueryString(req.getParameterMap());
        try{
            List<ReplyDto> replyList = replyService.commDetailList(commId,paging);
            log.info("replyList: "+replyList);
            model.addAttribute("replyList",replyList);
            model.addAttribute("paging",paging);
        }catch(Exception e){
            log.error(e.getMessage());
        }


        return "/community/reply/list";
    }

    @DeleteMapping("/delete.do")
    public @ResponseBody AjaxStateHandler delete(int replyId,
                                                 @SessionAttribute UserDto loginUser,
                                                 HttpSession session,
                                                 @SessionAttribute(required = false) String msg,
                                                 Model model){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        AjaxStateHandler ajaxStateHandler = new AjaxStateHandler();
        try{
            ReplyDto replyDto=replyService.detail(replyId);
            int remove = replyService.removeOne(replyId);
            ajaxStateHandler.setState(remove);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return ajaxStateHandler;

    }

    @PostMapping("/register.do")
    public @ResponseBody int register(ReplyDto reply,
                                      @SessionAttribute UserDto loginUser){
        int register=0;
        log.info("reply: "+ reply);
        try{
            register=replyService.registerOne(reply);
            log.info("register: "+ register);
            log.info("이제 register 다시 돌려보낸다.");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return register;
    }

    @GetMapping("/modify.do")
    public String modify( int replyId,
                         @SessionAttribute UserDto loginUser,
                         Model model,
                          HttpSession session,
                          @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        try{
            ReplyDto reply=replyService.detail(replyId);
            model.addAttribute("reply",reply);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return "/community/reply/modify";
    }
    @PutMapping("/modify.do")
    public @ResponseBody AjaxStateHandler modify(ReplyDto reply, @SessionAttribute UserDto loginUser){
        AjaxStateHandler ajaxStateHandler=new AjaxStateHandler();
        try{
            int modify=replyService.modifyOne(reply);
            ajaxStateHandler.setState(modify);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return ajaxStateHandler;

    }


}
