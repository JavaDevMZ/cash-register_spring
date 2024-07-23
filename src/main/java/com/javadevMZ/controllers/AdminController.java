package com.javadevMZ.controllers;

import com.javadevMZ.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/new_user")
    public ModelAndView showNewUserPage(ModelAndView modelAndView) {
        modelAndView.setViewName("new_user");
        return modelAndView;
    }

    @PostMapping(value = "/new_user")
  public String registerUser(@RequestParam String email, @RequestParam String password,
                                 @RequestParam String roleName) {
        System.out.println("POST FOR NEW_USER");
       User user = User.builder().email(email).password(password).role(roleRepository.findByRole(Role.valueOf(roleName))).build();
   repository.save(user);

   return "redirect:/";
    }
}
