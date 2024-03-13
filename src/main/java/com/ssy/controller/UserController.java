package com.ssy.controller;

import com.ssy.Entity.User;
import com.ssy.service.UserService;
import com.ssy.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public int Login(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response){
        return userService.Login(user,request,response);
    }
}
