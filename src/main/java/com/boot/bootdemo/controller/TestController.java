package com.boot.bootdemo.controller;

import com.boot.bootdemo.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@Controller*/
@Controller
public class TestController {


    @RequestMapping("/index")
    public String index(Model model){
        System.out.println("请求进入了index");
        return "index";
    }

    @RequestMapping("/login")
    public String userLogin(){
        System.out.println("请求进入了login");
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(){
        return "login-error";
    }


    @RequestMapping("/whoim")
    public Object whoIm(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
