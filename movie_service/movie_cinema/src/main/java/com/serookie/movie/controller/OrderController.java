package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Hall;
import com.serookie.entity.Order;
import com.serookie.entity.Result;
import com.serookie.movie.service.OrderService;
import com.serookie.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RefreshScope
@CrossOrigin
@Api(value = "订单的接口", tags = {"订单的接口"})
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/list/{current}/{limit}")
    @ApiOperation("订单的数据分页接口")
    public Result hallListPage(@PathVariable("current") Integer current, @PathVariable("limit") Integer limit){
        Page<Order> OrderPage = orderService.OrderPage(current, limit);
        return Result.ok().message("查询成功").data("total",OrderPage.getTotal()).data("order",OrderPage);
    }
    @PostMapping("/order/insert")
    @ApiOperation("订单的添加接口")
    public Result insertOrder(@RequestBody OrderVo orderVo){
       Boolean temp= orderService.insertOrder(orderVo);
       return temp ? Result.ok().message("添加成功"):Result.error().message("添加失败");
    }
    @GetMapping("/order/query")
    public Result queryByMovieId(@RequestParam("movieId") String movieId){
      List<Order> orders=orderService.findByMovieId(movieId);
      return Result.ok().message("查询成功").data("orders",orders);
    }
    @GetMapping("/order/query/{uid}")
    public Result queryByUserId(@PathVariable("uid") String uid){
        List<Order> orders=orderService.findByUid(uid);
        return Result.ok().message("查询成功").data("orders",orders);
    }
    @DeleteMapping("/order/{orderId}")
    public Result deleteOrder(@PathVariable("orderId") String orderId){
        boolean flag = orderService.removeById(orderId);
        return flag?Result.ok().message("删除成功"):Result.error().message("删除失败");
    }
    @GetMapping("/order/count")
    public Result orderCount(){
        long count = orderService.count();
        return Result.ok().message("查询成功").data("orderCount",count);
    }
}
