package com.serookie.controller;

import com.serookie.entity.Result;
import com.serookie.service.MsmService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/4
 */
@RestController
@CrossOrigin//跨域问题
@RequestMapping("/msm")
public class MsmController {

    @Resource
    private MsmService msmService;


    @GetMapping("/send/{email}")
    public Result send(@PathVariable("email") String email){
        boolean send = msmService.send(email);
        if(send){
            return Result.ok().message("发送成功");
        }else {
            return Result.error().message("发送失败");
        }
    }
    @GetMapping("/send/{email}/{message}/{orderId}")
    public Result sendMail(@PathVariable("email") String email,@PathVariable("message") String message,@PathVariable("orderId") String orderId){
        boolean flag=msmService.sendOrder(email,message,orderId);
        if(flag){
            return Result.ok().message("发送成功");
        }else {
            return Result.error().message("发送失败");
        }
    }
}
