package com.example.gagooda_project.controller;


import com.example.gagooda_project.dto.CommunityDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.ReplyDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CommunityService;
import com.example.gagooda_project.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Path;

import java.util.List;

@RequestMapping("/community")
@Controller
public class CommunityController {
    private CommunityService communityService;
    private ReplyService replyService;
    @Value("${img.upload.path}")
    private String imgPath;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CommunityController(CommunityService communityService,ReplyService replyService) {

        this.communityService = communityService;
        this.replyService = replyService;
    }

    @GetMapping("/list.do")
    public String list(PagingDto paging,
                       Model model,
                       HttpServletRequest req,
                       HttpSession session,
                       @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        if(paging.getOrderField()==null)paging.setOrderField("comm_id");
        paging.setRows(16);
        paging.setQueryString(req.getParameterMap());
        try{
            List<CommunityDto> communityList = communityService.communityList(paging);
            model.addAttribute("communityList",communityList);
            model.addAttribute("paging",paging);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return "/community/user/list";

    }

    @GetMapping("user_yes/{commId}/delete.do")
    public String delete(@PathVariable int commId,
                         HttpSession session,
                         Model model,
                         @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        int delete = 0;
        try{
            delete = communityService.deleteOne(commId);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        if(delete>0){
            session.setAttribute("msg","포스트 삭제에 성공하였습니다.");

        }else{
            session.setAttribute("msg","포스트 삭제에 실패하였습니다. 다시 시도해 주세요.");
        }
        return "redirect:/community/list.do";
    }

    @GetMapping("/{commId}/detail.do")
    public String detail(@PathVariable int commId,
                         Model model,
                         PagingDto paging,
                         HttpServletRequest req,
                         HttpSession session,
                         @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        try{
            paging.setQueryString(req.getParameterMap());
            CommunityDto community = communityService.detail(commId);
            List<ReplyDto> replyList = replyService.commDetailList(commId, paging);
            int repCount = replyService.countReply(commId);
            model.addAttribute("community",community);
            model.addAttribute("replyList",replyList);
            model.addAttribute("paging",paging);
            model.addAttribute("repCount",repCount);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "/community/user/detail";
    }
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           HttpSession session,
                           @SessionAttribute(required = false) String msg,
                           Model model
                           ){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        log.info("community register getMapping");
        return "/community/user/register";
    }

    @GetMapping("/user_yes/{commId}/modify.do")
    public String modify(@SessionAttribute UserDto loginUser,
                         @PathVariable int commId,
                         Model model,
                         HttpSession session,
                         @SessionAttribute(required = false) String msg){
        if (msg != null) {
            session.removeAttribute("msg");
            model.addAttribute("msg", msg);
        }
        try{
            CommunityDto community = communityService.selectOne(commId);
            model.addAttribute("community",community);

        }catch(Exception e){
            log.error(e.getMessage());
        }
        return "/community/user/modify";
    }
    @PostMapping("/user_yes/modify.do")
    public String modify(@SessionAttribute UserDto loginUser,
                         CommunityDto community,
                         @RequestParam(name="imgFile", required = false) List<MultipartFile> imgFileList,
                         @RequestParam(name="imgToDelete", required=false) List<String> imgToDeleteList,
                         HttpSession session){
        int modify = 0;
        log.info("imgToDeleteList: "+ imgToDeleteList);
        log.info("imgFileList Controller: "+imgFileList);
        if (loginUser.getUserId() == community.getUserId()){
            try{
                modify = communityService.update(imgFileList, community, imgPath, imgToDeleteList);

            }catch(Exception e){
                log.error(e.getMessage());
            }
        }
        if (modify >0 ){
            session.setAttribute("msg","포스트 수정에 성공하였습니다.");
            return "redirect:/community/"+community.getCommId()+"/detail.do";
        }else{
            session.setAttribute("msg","포스트 수정에 문제가 생겼습니다. 다시 시도해주십시오.");
            return "redirect:/community/"+community.getCommId()+"/modify.do";
        }
    }
    @PostMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser,
                           @RequestParam(name = "imgFile") List<MultipartFile> imgFileList,
                           HttpSession session,
                           CommunityDto community){
        int register = 0;
        if(loginUser.getUserId() == community.getUserId()){

            try{
                community.setImgCode("");
                register = communityService.register(imgFileList,community,imgPath);
                log.info("community register: "+register);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        if(register >0){
            log.info("community register completed");
            session.setAttribute("msg","포스트 등록에 성공하였습니다.");
            return "redirect:/community/list.do";
        }else{
            log.info("community register failed");
            session.setAttribute("msg","포스트 등록에 문제가 생겼습니다. 다시 시도해주십시오.");
            return "redirect:/community/user_yes/register.do";
        }

    }
}
