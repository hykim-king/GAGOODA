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
public class ErrorInterceptor implements HandlerInterceptor {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
//        session.removeAttribute("redirectUri");
        if(response.getStatus() != 200) {
            session.setAttribute("error", response.getStatus());
            response.sendRedirect("/error.do");
            return false;
        }
        return true; //true or false
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HttpSession session = request.getSession();
//        session.removeAttribute("redirectUri");
//        if(response.getStatus() != 200) {
//            session.setAttribute("error", response.getStatus());
//            response.sendRedirect("/error.do");
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
