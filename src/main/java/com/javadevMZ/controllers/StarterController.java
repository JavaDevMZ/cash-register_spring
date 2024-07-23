package com.javadevMZ.controllers;
import com.javadevMZ.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StarterController {


    private final UserService userService;

    public StarterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView showMainPage(ModelAndView modelAndView, Model model) throws Exception{
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
           return defineRole(modelAndView);
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView showLoginPage(ModelAndView modelAndView, Model model) throws Exception{

        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/test")
    public String showTestPage(ModelAndView modelAndView, Model model) throws Exception{
       return "test";
    }

    @PostMapping("/login")
    public String acceptLogin() throws Exception{
       return "/home";
    }

    @GetMapping("/home")
    public ModelAndView defineRole(ModelAndView modelAndView) throws Exception{
        List<String> roles = userService.getAuthorities();
        if(roles.contains("ROLE_ADMIN")){
            modelAndView.setViewName("admin_home");
        }else if(roles.contains("ROLE_CASHIER") || roles.contains("ROLE_SENIOR_CASHIER")){
            modelAndView.setViewName("cashier_home");
        }else if(roles.contains("ROLE_COMMODITY_EXPERT")){
            modelAndView.setViewName("commodity-expert_home");
        }else{

            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public void logout(){
        SecurityContextHolder.clearContext();

    }
}
