package com.example.gagooda_project.controller;


import com.example.gagooda_project.dto.CommunityDto;
import com.example.gagooda_project.dto.PagingDto;
import com.example.gagooda_project.dto.UserDto;
import com.example.gagooda_project.service.CommunityService;
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

import java.util.List;

@RequestMapping("/community")
@Controller
public class CommunityController {
    private CommunityService communityService;
    @Value("${img.upload.path}")
    private String imgPath;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/list.do")
    public String list(PagingDto paging,
                       Model model,
                       HttpServletRequest req){
        if(paging.getOrderField()==null)paging.setOrderField("comm_id");
        paging.setQueryString(req.getParameterMap());
        try{
            List<CommunityDto> communityList = communityService.communityList(paging);
            model.addAttribute("communityList",communityList);
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return "/community/user/list";


    }

    @GetMapping("/{commId}/detail.do")
    public String detail(@PathVariable int commId,
                         Model model){
        try{
            CommunityDto community = communityService.selectOne(commId);
            model.addAttribute("community",community);

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "/community/user/detail";
    }
    @GetMapping("/user_yes/register.do")
    public String register(@SessionAttribute UserDto loginUser
                           ){
        log.info("community register getMapping");
        return "/community/user/register";
    }

    @GetMapping("/user_yes/{commId}/modify.do")
    public String modify(@SessionAttribute UserDto loginUser,
                         @PathVariable int commId,
                         Model model){
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
                         CommunityDto community){
        return null;
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
            return "redirect:/community/list.do";
        }else{
            log.info("community register failed");
            return "redirect:/community/user_yes/register.do";
        }

    }
}
