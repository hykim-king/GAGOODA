package com.example.gagooda_project.interceptor;

import com.example.gagooda_project.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AdminCheckInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginUser_obj=session.getAttribute("loginUser");
        UserDto loginUser = (UserDto) loginUser_obj;
        String uri = request.getRequestURI();
        log.info("preHandle(uri) : "+ uri);
        if(loginUser_obj==null || !loginUser.getGDet().equals("g1")) {
            session.setAttribute("msg", "접근 권한이 없습니다.");
            response.sendRedirect("/");
            return false;
        }
        return true; //true or false
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
