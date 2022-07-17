package com.boop.love_sing.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/test/")
public class TestControler {

    @GetMapping("/hello")
    public String hello(){
        return "Hello !!!";
    }
}
