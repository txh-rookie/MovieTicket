package com.serookie.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Movie;
import com.serookie.entity.MovieVoQuery;
import com.serookie.entity.Result;
import com.serookie.movie.service.MovieService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kevintam
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/movie")
@CrossOrigin
@Api(value="电影影片接口",tags={"电影影片接口"})
public class MovieController {
   @Resource
   private MovieService movieService;

   @GetMapping("/findAll")
   @ApiOperation("查询所有的影片")
    public Result findAll(){
       List<Movie> list = movieService.list();
       return Result.ok().message("查询成功").data("movie",list);
   }
   @DeleteMapping("/film/{id}")
   @ApiOperation("根据id删除数据")
   public Result film(@ApiParam("影片的id") @PathVariable("id") Integer id){
      Movie movie = movieService.getById(id);
      if(movie==null){
         Result.error().message("删除失败");
      }
      boolean flag = movieService.removeById(id);
      return flag?Result.ok().message("删除成功").data("movie",movie):Result.error().message("删除失败");
   }
   @GetMapping("/film/{current}/{limit}")
   @ApiOperation("影片的数据分页")
   public Result PageMovie(@ApiParam("当前页参数") @PathVariable("current") Integer current,
                           @ApiParam("每页的记录数")@PathVariable("limit") Integer limit){
      Page<Movie> page = movieService.PageMovie(current, limit);
      return Result.ok().message("查询成功").data("total",page.getTotal()).data("rows",page);
   }
   @ApiOperation("带有条件查询影片数据分页")
   @PostMapping("/film/{current}/{limit}")
   public Result ConditionQuery(@ApiParam("当前页")@PathVariable("current") Integer current,
                               @ApiParam("每页的记录数") @PathVariable("limit")Integer limit,
                                @RequestBody(required = false)  MovieVoQuery movieVoQuery){
      if(movieVoQuery == null){
         Result result = PageMovie(current, limit);
         return result;
      }
      Page<Movie> page = movieService.ConditionQuery(current, limit, movieVoQuery);
      return Result.ok().message("查询成功").data("info",page);
   }
   @PostMapping("/film")
   @ApiOperation("添加电影")
   public Result insertFilm(@RequestBody Movie movie){
      boolean flag = movieService.save(movie);
      if(flag){
         return Result.ok().message("添加完成");
      }else{
         return Result.error().message("添加失败");
      }
   }
   @PutMapping("/film")
   @ApiOperation("修改电影")
   public Result updateFilm(@RequestBody Movie movie){
      boolean flag = movieService.updateById(movie);
      if(flag){
        return Result.ok().message("修改成功");
      }else {
         return Result.error().message("修改失败");
      }
   }
}

