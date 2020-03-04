package com.swpu.shop.shopflash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 9:53
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
