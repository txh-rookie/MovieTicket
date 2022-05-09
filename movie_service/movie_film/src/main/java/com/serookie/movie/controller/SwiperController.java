package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Result;
import com.serookie.entity.Swiper;
import com.serookie.movie.CustomAnnotation.LogAnnotation;
import com.serookie.movie.service.SwiperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/3/14
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/swiper")
@Api(value="轮播图接口",tags={"轮播图接口"})
public class SwiperController {
    @Resource
    private SwiperService swiperService;

    @GetMapping("/all/{current}/{limit}")
    @LogAnnotation(operModul = "轮播图模块",operType = "查询",operDesc = "查询所有图片")
    @ApiOperation("查询所有的影片")
    public Result pageList(@ApiParam("当前页参数") @PathVariable("current") Integer current,@ApiParam("每页的记录数") @PathVariable("limit") Integer limit){
        Page<Swiper> swiperPage = swiperService.PageMovie(current, limit);
        return Result.ok().message("查询成功").data("swiperList",swiperPage);
    }
    //查询所有的轮播图
    @GetMapping("/list")
    @LogAnnotation(operModul = "轮播图模块",operType = "查询",operDesc = "查询所有的图片")
    @ApiOperation("查询所有的轮播图")
    public Result all(){
        List<Swiper> lists= swiperService.all();
        return Result.ok().message("查询成功").data("swipers",lists);
    }
    @PostMapping("/insert")
    @LogAnnotation(operModul = "轮播图模块",operType = "添加",operDesc = "添加轮播图模块")
    @ApiOperation("添加轮播图")
    public Result addSwiper(@RequestBody Swiper swiper){
        Boolean flag = swiperService.insertSwiper(swiper);
        return flag? Result.ok().message("添加轮播图成功"):Result.error().message("添加失败");
    }
    @DeleteMapping("/{swiperId}")
    @LogAnnotation(operModul = "轮播图模块",operType = "删除",operDesc = "根据id进行轮播图模块")
    @ApiOperation("删除轮播图")
    public Result delSwiper(@PathVariable("swiperId")String swiperId){
        Boolean flag = swiperService.delSwiper(swiperId);
        return flag? Result.ok().message("删除轮播图成功"):Result.error().message("删除失败");
    }
    @PutMapping("/")
    @LogAnnotation(operModul = "轮播图模块",operType = "编辑",operDesc = "更改轮播图的状态")
    @ApiOperation("编辑轮播图")
    public Result editSwiper(@RequestBody Swiper swiper){
        boolean flag = swiperService.updateById(swiper);
        return flag?Result.ok().message("编辑轮播图成功"):Result.error().message("编辑轮播图失败");
    }
    @GetMapping("/count")
    public Result swiperCount(){
        long count = swiperService.count();
        return Result.ok().message("查询成功").data("swiperCount",count);
    }
}
