package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Cart;
import com.serookie.entity.Result;
import com.serookie.movie.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/19
 */
@RestController
@CrossOrigin
@RefreshScope
@Api(value = "购物车接口", tags = {"购物车接口"})
public class CartController {

    @Resource
    private CartService cartService;

    @GetMapping("/cart/list/{current}/{limit}")
    public Result pageListCart(@PathVariable("current") Integer current,@PathVariable("limit") Integer limit){
        Page<Cart> cartPage = cartService.CartPage(current, limit);
        return Result.ok().message("查询成功").data("total",cartPage.getTotal()).data("rows",cartPage);
    }

    @PostMapping("/cart/insert")
    public Result insertCart(@RequestBody Cart cart){
        Boolean flag = cartService.insertCart(cart);
        return flag?Result.ok().message("添加成功"):Result.error().message("查询失败");
    }
//    @GetMapping("/cart/query")
//    public Result queryCart(@RequestParam("movieId") String movieId){
//
//    }
    @GetMapping("/cart/{uid}")
    public Result queryCart(@PathVariable("uid") String uid){
        List<Cart> carts= cartService.queryCartByUid(uid);
        return Result.ok().message("查询成功").data("carts",carts);
    }
    @DeleteMapping("/cart/{cartId}")
    public Result deleteCart(@PathVariable("cartId") String cartId){
        boolean flag = cartService.removeById(cartId);
        return flag?Result.ok().message("删除成功"):Result.error().message("删除失败");
    }
}
