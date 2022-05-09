package com.serookie.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Movie;
import com.serookie.entity.Result;
import com.serookie.entity.User;
import com.serookie.entity.UserRole;
import com.serookie.movie.utils.JwtTokenUtils;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.service.UserRoleService;
import com.serookie.service.UserService;
import com.serookie.vo.RegisterVo;
import com.serookie.vo.UserAddVo;
import com.serookie.vo.UserLogin;
import com.serookie.vo.UserVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/7
 */
@RestController
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;

    @Resource
    private JwtTokenUtils jwtTokenUtils;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/user/login")
    @ApiOperation("登录")
    public Result login(@RequestBody UserLogin userLogin){
        //登录
        String token=  userService.login(userLogin);
        String userId = jwtTokenUtils.getToken(token);
        //生成token
        return Result.ok().message("登录成功").data("token",token).data("id",userId);
    }
    @GetMapping("/user/logout")
    public Result logout(@RequestParam("token") String token){
        redisTemplate.delete(token);
        return Result.ok();
    }
    @PostMapping("/user/register")
    @ApiOperation("注册")
    public Result register(@RequestBody RegisterVo registerVo){
         userService.register(registerVo);
         return Result.ok().message("注册成功");
    }
    @GetMapping("/user/info")
    public Result userInfo(@RequestParam("token") String token){
        User user=userService.getUserInfo(token);
        return Result.ok().message("查询成功").data("info",user);
    }
    @GetMapping("/user/{id}")
    @ApiOperation("根据id进行验证")
    public Result getById(@PathVariable("id") String id){
        User user = userService.selectById(id);
        return Result.ok().message("查询成功").data("user",user);
    }
    @GetMapping("/user/list/{current}/{limit}")
    @ApiOperation("查询所有的用户")
    public Result userPageList(@PathVariable("current") Integer current,@PathVariable("limit") Integer limit){
        Page<User> page = userService.PageUser(current, limit);
        return Result.ok().message("查询成功").data("total",page.getTotal()).data("rows",page);
    }
    @DeleteMapping("/user/{userId}")
    public Result deleteUser(@PathVariable("userId") String userId){
        userService.removeById(userId);
        return Result.ok();
    }
    @PostMapping("/user/saveUser")
    public Result saveUser(@RequestBody UserAddVo userAddVo){
        User user = new User();
        user.setUsername(userAddVo.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(userAddVo.getPassword().getBytes(StandardCharsets.UTF_8)));
        user.setUserSex(1);
        user.setNickname(userAddVo.getUsername());
        user.setEmail("843808107@qq.com");
        user.setHeadUrl("https://apic.douyucdn.cn/upload/avatar_v3/201807/125191d284e40805300533323fce2f18_middle.jpg");
        System.out.println(user);
        userService.save(user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userAddVo.getUsername());
        User userServiceOne = userService.getOne(queryWrapper);
        UserRole userRole = new UserRole();
        userRole.setUid(userServiceOne.getUserId());
        userRole.setRid(userAddVo.getRoleId());
        userRoleService.save(userRole);
        return Result.ok();
    }

    @PutMapping("/user/update")
    @ApiOperation("根据userId进行更新")
    public Result updateUser(@RequestBody User user){
        if(StringUtils.isEmpty(user)){
            throw new CustomException(2001,"更新失败");
        }
        boolean flag = userService.updateById(user);
        User user1 = userService.selectById(user.getUserId());
        return flag?Result.ok().message("更新成功").data("user",user1):Result.error().message("更新失败");
    }
    @GetMapping("/user/count")
    public Result userCount(){
        long count = userService.count();
        return Result.ok().message("查询成功").data("userCount",count);
    }
    @GetMapping("/user/sexCount")
    public Result userSexCount(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_sex","1");
        long count = userService.count(queryWrapper);
        return Result.ok().message("查询成功").data("sexCount",count);
    }
    @GetMapping("/user/collection")
    @ApiOperation("收藏")
    public Result collection(@RequestParam("movieId") String movieId,@RequestParam("uid") String uid){
        if(StringUtils.isEmpty(movieId)){
            throw new CustomException(20001,"电影id不能为空");
        }
        if(StringUtils.isEmpty(uid)){
            throw new CustomException(20001,"用户id不能为空");
        }
        User user = userService.getById(uid);
        if(StringUtils.isEmpty(user)){
            throw new CustomException(20001,"用户id不正确");
        }
        String collection = user.getCollection();
        if(StringUtils.isEmpty(collection)){
            user.setCollection(movieId);
            userService.updateById(user);
            return Result.ok().message("收藏成功");
        }
        String[] split = collection.split("#");
        for (String str:split
             ) {
            if(str.equals(movieId)){
                throw new CustomException(20001,"不可重复收藏");
            }
        }
        StringBuilder stringBuilder = new StringBuilder(collection);
        StringBuilder append = stringBuilder.append("#" + movieId);
        user.setCollection(append.toString());
        return Result.ok().message("收藏成功");
    }
}
