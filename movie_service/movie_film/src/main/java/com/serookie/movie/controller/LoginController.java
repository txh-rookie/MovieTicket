package com.serookie.movie.controller;

import com.serookie.entity.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")//模拟登录
@RequestMapping("/movie")
public class LoginController {

    @PostMapping("/login")
    public Result login(){
       return Result.ok().message("登录成功").data("token","admin").data("name","https://www.static.talkxj.com/avatar/user.png");
    }
    @GetMapping("/info")
    public Result info(){
        return Result.ok().message("查询成功").data("token","admin").data("name","admin").data("avatar","https://www.static.talkxj.com/avatar/user.png");
    }
}
