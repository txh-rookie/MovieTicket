package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Hall;
import com.serookie.entity.Result;
import com.serookie.entity.Schedule;
import com.serookie.movie.CustomAnnotation.LogAnnotation;
import com.serookie.movie.service.HallService;
import com.serookie.movie.service.ScheduleService;
import com.serookie.vo.MovieVoQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/1/1
 */
@RestController
@CrossOrigin
@Api(value = "场次的接口", tags = {"影厅场次的接口"})
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;
    @Resource
    private HallService hallService;

    @GetMapping("/schedule/list/{current}/{limit}")
    @ApiOperation("场次的数据分页")
    public Result schedulePage(@PathVariable("current") Integer current,@PathVariable("limit") Integer limit){
        Page<Schedule> page = scheduleService.page(current, limit);
        return Result.ok().message("查询成功").data("total",page.getTotal()).data("rows",page);
    }

    @PostMapping("/schedule")
    @ApiOperation("场次的添加")
    public Result addSchedule(@RequestBody Schedule schedule){
        if(schedule.getScheduleRemain()==null){
            Hall hall = hallService.getById(schedule.getHallId());
            schedule.setScheduleRemain(hall.getHallCapacity());
        }
        boolean flag = scheduleService.save(schedule);
        return flag?Result.ok().message("添加成功"):Result.error().message("添加失败");
    }

//    /**
//     * 模糊查询
//     * @param search
//     * @return
//     */
//    @GetMapping("/schedule/search")
//    @ApiOperation("模糊查询")
//    public Result searchSchedule(@RequestParam("search") String search){
//        List<Schedule> schedules = scheduleService.searchBySchedule(search);
//        return Result.ok().message("查询成功").data("searchList",schedules);
//    }
    @PutMapping("/schedule")
    @ApiOperation("场次的修改")
    public Result updateSchedule(@RequestBody Schedule schedule){
        boolean flag = scheduleService.updateById(schedule);
        return flag?Result.ok().message("修改成功"):Result.error().message("修改失败");
    }
    @GetMapping("/schedule/{id}")
    @ApiOperation("根据id进行查询")
    public Result getScheduleById(@PathVariable("id") String id){
        Schedule schedule = scheduleService.findById(id);
        return Result.ok().message("查询成功").data("schedule",schedule);
    }
    @DeleteMapping("/schedule/{id}")
    @ApiOperation("根据id进行删除")
    public Result deleteSchedule(@PathVariable("id") String id){
        boolean flag = scheduleService.removeById(id);
        return flag?Result.ok().message("删除成功"):Result.error().message("删除失败");
    }
    @GetMapping("/schedule/cinema")
    @ApiOperation("根据movieId查询")
    public Result getByMovieId(@RequestParam("movieId") String movieId){
        List<Schedule> schedules= scheduleService.findByMovieId(movieId);
        return Result.ok().message("查询成功").data("schedules",schedules);
    }
    @GetMapping("/schedule/movie")
    @ApiOperation("根据cinemaId查询")
    public Result getByCinemaId(@RequestParam("cinemaId") String cinemaId){
        List<Schedule> schedules= scheduleService.findByCinemaId(cinemaId);
        return Result.ok().message("查询成功").data("schedules",schedules);
    }
    @GetMapping("/schedule/count")
    public Result scheduleCount(){
        long count = scheduleService.count();
        return Result.ok().message("查询成功").data("scheduleCount",count);
    }
}
