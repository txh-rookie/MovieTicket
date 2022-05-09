package com.serookie.controller;

import com.serookie.entity.Permission;
import com.serookie.entity.Result;
import com.serookie.entity.User;
import com.serookie.service.UserService;
import com.serookie.vo.UserLogin;
import com.serookie.vo.UserVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/5/1
 */
@RestController
@Api("管理员登录")
@CrossOrigin//解决跨域问题
public class AdminController {

    @Resource
    private UserService userService;

    @GetMapping("/admin/login")
    public Result loginAdmin(@RequestBody UserLogin user) {
        String token = userService.login(user);
        return Result.ok().message("登录成功").data("token", token);
    }
    @GetMapping("/admin/index/menu")
    public Result adminInfo(@PathVariable("roleName") String roleName) {
        List<Permission> permissionList= userService.getMenu(roleName);
        return Result.ok().message("查询成功").data("permissionList",permissionList);
    }
}
