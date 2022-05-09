package com.serookie.movie.controller;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Cinema;
import com.serookie.entity.Result;
//import com.serookie.movie.service.OpneFign.AreaService;
import com.serookie.movie.service.CinemaService;
import com.serookie.movie.utils.handler.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/25
 */
@RestController
@CrossOrigin
@RefreshScope
@Api(value = "电影院接口", tags = {"电影院接口"})
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

//    @Autowired
//    private AreaService areaService;

    @GetMapping("/cinema/list/{current}/{limit}")
    @ApiOperation("影院的数据分页")
    public Result PageMovie(@ApiParam("当前页参数") @PathVariable("current") Integer current,
                            @ApiParam("每页的记录数")@PathVariable("limit") Integer limit){
//        Result area = areaService.getArea(address);
        Page<Cinema> cinemaPage = cinemaService.PageCinema(current, limit);
        return Result.ok().message("查询成功").data("total",cinemaPage.getTotal()).data("rows",cinemaPage);
    }
    @GetMapping("/cinema/all")
    @ApiOperation("影院的全部数据")
    public Result all(){
        List<Cinema> list = cinemaService.findAll();
        return Result.ok().message("查询成功").data("cinema",list);
    }
    @PutMapping("/cinema")
    @ApiOperation("修改影院的接口")
    public Result updateCinema(@RequestBody Cinema cinema){
        Cinema cinema1 = cinemaService.getById(cinema.getCinemaId());
        if(cinema1==null){
             return Result.error().message("修改数据失败");
        }
        boolean flag = cinemaService.updateCinema(cinema);
        return flag?Result.ok().message("修改成功"):Result.error().message("修改失败").data("data",null);
    }
    @PostMapping("/cinema")
    @ApiOperation("添加影院接口")
    public Result saveCinema(@RequestBody Cinema cinema){
        if(cinema==null){
            return Result.error().message("添加失败");
        }
        boolean flag = cinemaService.addCinema(cinema);
        return flag?Result.ok().message("添加影院成功"):Result.error().message("添加影院失败");
    }
    @DeleteMapping("/cinema/{id}")
    @ApiOperation("根据id进行逻辑删除影院接口")
    public Result deleteCinema(@PathVariable("id") String id ){
        try {
            cinemaService.delCinema(id);
        } catch (Exception e) {
            throw new CustomException(3000,"删除失败");
        }
        return Result.ok().message("删除成功");
    }
    @GetMapping("/cinema/{id}")
    @ApiOperation("根据id进行查询")
    public Result getCinemaById(@PathVariable("id") Long id){
        Cinema cinema = cinemaService.findByCinemaId(id);
        if(cinema==null){
            throw new CustomException(4000,"查询失败");
        }
        return Result.ok().message("查询成功").data("cinema",cinema);
    }
    @GetMapping("/cinema/search")
    @ApiOperation("根据服务和品牌进行查询")
    public Result getCinemaSearch(@RequestParam("name") String name,@RequestParam("service") String service){
        List<Cinema> all = cinemaService.searchByNameAndService(name,service);
        return Result.ok().message("查询成功").data("cinemas",all);
    }
    @GetMapping("/cinema/count")
    @ApiOperation("统计影院的总数")
    public Result cinemaCount(){
        long count = cinemaService.count();
        return Result.ok().message("查询成功").data("cinemaCount",count);
    }
}
