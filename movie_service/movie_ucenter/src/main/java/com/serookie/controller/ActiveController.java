package com.serookie.controller;

import com.serookie.entity.Active;
import com.serookie.entity.Result;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.service.ActiveService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/22
 */
@RestController
@CrossOrigin//跨域问题
@RequestMapping("/active")
public class ActiveController {

    @Resource
    private ActiveService activeService;

    @PostMapping("/insert")
    public Result insertActive(@RequestBody Active active){
        if(StringUtils.isEmpty(active)){
            throw new CustomException(2001,"添加失败");
        }
        boolean flag = activeService.save(active);
        return flag?Result.ok().message("添加成功"):Result.error().message("查询失败");
    }
    @GetMapping("/findAll")
    public Result findAll(){
        List<Active> list = activeService.list();
        if(StringUtils.isEmpty(list)){
            throw new CustomException(2001,"查询失败");
        }
        return Result.ok().message("查询成功").data("actives",list);
    }
    @GetMapping("/sign")
    public Result signUp(@RequestParam("uid") String uid,@RequestParam("activeId") String activeId){
        Boolean flag= activeService.signUp(uid,activeId);
        return flag?Result.ok().message("成功"):Result.error().message("失败");
    }
}
