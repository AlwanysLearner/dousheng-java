package com.ssy.controller;

import com.ssy.Entity.User;
import com.ssy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dousheng/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public int Register(@ModelAttribute User user){
        return userService.Create(user);
    }
    @PostMapping("/login")
    public String Login(@ModelAttribute User user, HttpServletRequest request){
        return userService.Search(user,request);
    }
}
