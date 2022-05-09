package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Hall;
import com.serookie.entity.Result;
import com.serookie.movie.CustomAnnotation.LogAnnotation;
import com.serookie.movie.service.HallService;
import com.serookie.vo.HallVo;
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
 * @createDate 2022/1/1
 */
@RestController
@RefreshScope
@CrossOrigin
@Api(value = "影厅的接口", tags = {"影厅的接口"})
public class HallController {

    @Resource
    private HallService hallService;


    @GetMapping("/hall/list/{current}/{limit}")
    @ApiOperation("影厅的数据分页接口")
    public Result hallListPage(@PathVariable("current") Integer current,@PathVariable("limit") Integer limit){
        Page<Hall> hallPage = hallService.HallPage(current, limit);
        return Result.ok().message("查询成功").data("total",hallPage.getTotal()).data("hall",hallPage);
    }
    @PutMapping("/hall")
    @ApiOperation("修改影厅的接口")
    public Result UpdateHall(@RequestBody Hall hall){
        Hall byHall = hallService.getById(hall.getHallId());
        if(byHall==null){
            return Result.error().message("修改失败");
        }
        boolean flag = hallService.updateById(hall);
        return flag?Result.ok().message("修改成功"):Result.error().message("修改失败");
    }
    @PostMapping("/hall")
    @ApiOperation("影厅的添加接口")
    public Result addHall(@RequestBody Hall hall){
        //默认的放映厅数量
        if(hall.getHallCapacity()==null){
            hall.setHallRow(12);
            hall.setHallColumn(12);
            hall.setHallCapacity(hall.getHallRow()*hall.getHallColumn());
        }
        boolean flag = hallService.save(hall);
        return flag?Result.ok().message("添加成功"):Result.error().message("添加失败");
    }
    @GetMapping("/hall/{id}")
    @ApiOperation("根据id查询影厅")
    public Result hallById(@PathVariable("id") String id){
        Hall hall = hallService.getById(id);
        return Result.ok().message("查询影厅成功").data("hall",hall);
    }
    @DeleteMapping("/hall/{id}")
    @ApiOperation("根据id进行删除")
    public Result deleteHall(@PathVariable("id") Long id){
        boolean flag = hallService.removeById(id);
        return flag ? Result.ok().message("删除成功"):Result.error().message("删除失败");
    }
    @PostMapping("/hall/seat")
    @ApiOperation("修改hall的厅位")
    @LogAnnotation(operModul = "影厅模块",operType = "影厅数据分页",operDesc = "带有条件查询影厅数据分页")
    public Result updateHallCapacity(@RequestBody HallVo hallVo){
        Integer CapacityCount=hallVo.getHallRow()* hallVo.getHallColumn();
        Hall hall = hallService.getById(hallVo.getHallId());
        hall.setHallRow(hallVo.getHallRow());
        hall.setHallColumn(hallVo.getHallColumn());
        hall.setHallCapacity(CapacityCount);
        boolean flag = hallService.updateById(hall);
        return flag?Result.ok().message("修改厅位成功"):Result.error().message("修改厅位失败");
    }
    //查询所有的
    @GetMapping("/hall/all")
    @ApiOperation("查询所有的信息")
    @LogAnnotation(operModul = "影厅模块",operType = "查询所有的数据",operDesc = "查询全部的影厅数据")
    public Result findAll(){
        List<Hall> list = hallService.list();
        return Result.ok().message("查询成功").data("hallAll",list);
    }
    @GetMapping("/hall")
    @ApiOperation("根据影院id查询影厅")
    public Result getCinemaIdByHall(@RequestParam("cinemaId") Long cinemaId){
        List<Hall> cinemaIdByHall = hallService.getCinemaIdByHall(cinemaId);
        return cinemaIdByHall.isEmpty()?Result.error().message("查询失败"):Result.ok().message("查询成功").data("halls",cinemaIdByHall);
    }
    @GetMapping("/hall/count")
    public Result hallCount(){
        long count = hallService.count();
        return Result.ok().message("查询成功").data("hallCount",count);
    }
}
