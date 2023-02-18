package com.example.gagooda_project;

import com.example.gagooda_project.interceptor.AdminCheckInterceptor;
import com.example.gagooda_project.interceptor.ErrorInterceptor;
import com.example.gagooda_project.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    LoginCheckInterceptor loginCheckInterceptor;
    AdminCheckInterceptor adminCheckInterceptor;
    ErrorInterceptor errorInterceptor;
    public InterceptorConfig(LoginCheckInterceptor loginCheckInterceptor,
                             AdminCheckInterceptor adminCheckInterceptor,
                             ErrorInterceptor errorInterceptor) {
        this.loginCheckInterceptor = loginCheckInterceptor;
        this.adminCheckInterceptor = adminCheckInterceptor;
        this.errorInterceptor = errorInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**/user_yes/**/");
        registry.addInterceptor(adminCheckInterceptor)
                .addPathPatterns("/**/admin/*.do")
                .excludePathPatterns("/user/admin/register.do");
//        registry.addInterceptor(errorInterceptor);
    }
}
