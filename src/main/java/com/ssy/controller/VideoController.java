package com.ssy.controller;

import com.ssy.Entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
    @GetMapping("/dousheng/auth/1")
    public int Register(@ModelAttribute User user){
        return 1;
    }
}
