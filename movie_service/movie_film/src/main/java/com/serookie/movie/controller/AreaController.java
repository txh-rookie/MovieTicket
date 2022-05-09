package com.serookie.movie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.serookie.entity.Area;
import com.serookie.entity.Result;
import com.serookie.movie.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/28
 */
@RestController
@CrossOrigin
@RefreshScope
@Api(value="地区接口",tags={"地区接口"})
public class AreaController {

    @Resource
    private AreaService areaService;

    @GetMapping("/area/list")
    @ApiOperation("以层级的方式展示所有的地区")
    public Result getArea(){
        List<Area> area = areaService.getArea();
        return Result.ok().message("查询成功").data("area",area);
    }
    @DeleteMapping("/area")
    @ApiOperation("删除地区")
    public Result delete(@RequestBody Integer[] ids){
        areaService.removeByIds(Arrays.asList(ids));
        return Result.ok().message("删除成功");
    }
    @GetMapping("/area/count")
    @ApiOperation("查询所有的地区数")
    public Result count(){
        long count = areaService.count();
        return Result.ok().message("操作成功").data("count",count);
    }
    /**
     * 新增商品分类
     * @param area
     * @return
     */
    @PostMapping("/area")
    @ApiOperation("新增地区")
    public Result save(@RequestBody Area area){
        boolean save = areaService.save(area);
        if(save){
            return Result.ok().message("新增完成");
        }
        return Result.error().message("新增失败");
    }

    /**
     * 修改商品分类
     * @param area
     * @return
     */
    @PutMapping("/area")
    @ApiOperation("修改地区")
    public Result update(@RequestBody Area area){
        boolean flag = areaService.updateById(area);
        if(flag){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }
    @GetMapping("/area/{id}")
    @ApiOperation("根据id查询地区")
    public Result info(@PathVariable("id") Integer id){
        Area area= areaService.getById(id);
        return Result.ok().message("查询成功").data("area",area);
    }
    @GetMapping("/area")
    @ApiOperation("根据name进行查询")
    public Result getName(String name){
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("area_name",name);
        Area area = areaService.getOne(wrapper);
        return Result.ok().message("查询成功").data("area",area);
    }
}
