package com.serookie.movie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.movie.CustomAnnotation.LogAnnotation;
import com.serookie.entity.Movie;
import com.serookie.vo.MovieVoQuery;
import com.serookie.entity.Result;
import com.serookie.movie.service.MovieService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

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
@RefreshScope
@Api(value="电影影片接口",tags={"电影影片接口"})
public class MovieController {

   @Resource
   private MovieService movieService;

   @Resource
   private RestTemplate restTemplate;

   @Value("${service.url}")
   private String serviceUrl;

   @GetMapping("/findAll")
   @LogAnnotation(operModul = "影片模块",operType = "查询",operDesc = "查询所有数据")
   @ApiOperation("查询所有的影片")
    public Result findAll(){
       List<Movie> list = movieService.list();
      List<Movie> movies = list.stream().filter(elem -> {
        return elem.getMovieState()==1;
      }).collect(Collectors.toList());
      return Result.ok().message("查询成功").data("movie",movies);
   }
   @GetMapping("/{id}")
   @ApiOperation("根据id进行查询")
   @LogAnnotation(operModul = "影片模块",operType = "查询",operDesc = "根据id查询影片")
   public Result getMovie(@PathVariable("id") Long id){
      Movie byId = movieService.getById(id);
      return Result.ok().message("查询成功").data("movie",byId);
   }
   @DeleteMapping("/film/{id}")
   @ApiOperation("根据id删除数据")
   @LogAnnotation(operModul = "影片模块",operType = "删除",operDesc = "根据id进行删除")
   public Result film(@ApiParam("影片的id") @PathVariable("id") String id){
      Movie movie = movieService.getById(id);
      if(movie==null){
         Result.error().message("删除失败");
      }
      boolean flag = movieService.removeById(id);
      return flag?Result.ok().message("删除成功").data("movie",movie):Result.error().message("删除失败");
   }
   @GetMapping("/film/{current}/{limit}")
   @ApiOperation("影片的数据分页")
   @LogAnnotation(operModul = "影片模块",operType = "数据分页",operDesc = "影片的数据分页")
   public Result PageMovie(@ApiParam("当前页参数") @PathVariable("current") Integer current,
                           @ApiParam("每页的记录数")@PathVariable("limit") Integer limit){
      Page<Movie> page = movieService.PageMovie(current, limit);
      return Result.ok().message("查询成功").data("total",page.getTotal()).data("rows",page);
   }
   @ApiOperation("带有条件查询影片数据分页")
   @PostMapping("/film/{current}/{limit}")
   @LogAnnotation(operModul = "影片模块",operType = "数据分页",operDesc = "带有条件查询影片数据分页")
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
   @LogAnnotation(operModul = "影片模块",operType = "添加",operDesc = "添加电影")
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
   @LogAnnotation(operModul = "影片模块",operType = "修改",operDesc = "修改电影")
   public Result updateFilm(@RequestBody Movie movie){
      boolean flag = movieService.updateById(movie);
      if(flag){
        return Result.ok().message("修改成功");
      }else {
         return Result.error().message("修改失败");
      }
   }
   @GetMapping("/film/hot/{num}")
   @ApiOperation("热门10")
   public Result hotMovie(@PathVariable("num") Integer num){
      List<Movie> hots= movieService.hotMovie(num);
      return Result.ok().message("查询成功").data("hots",hots);
   }
   @GetMapping("/films")
   @ApiOperation("根据类型和地区查找电影")
   public Result findMovieByType(@RequestParam("country") String country,@RequestParam("type") String type){
      List<Movie> movieList=movieService.findByTypeAndCountry(country,type);
      return Result.ok().message("查询成功").data("movieList",movieList);
   }
   //根据name查询
   @GetMapping("/search")
   @ApiOperation("根据电影名称进行查询")
   public Result searchByMovieName(@RequestParam("name") String name){
        List<Movie> movieList=movieService.searchByMovieName(name);
        return Result.ok().message("查询成功").data("movies",movieList);
   }
   @GetMapping("/count")
   public Result movieCount(){
      long count = movieService.count();
      return Result.ok().message("查询成功").data("movieCount",count);
   }
   @GetMapping("/boxOffice")
   public Result boxOffice(){
     Long boxOffices=movieService.boxOffice();
     return Result.ok().message("查询成功").data("boxOffice",boxOffices);
   }
   @GetMapping("/ranking")
   public Result rankings(){
      List<Movie> ranking= movieService.ranking();
      return Result.ok().message("查询成功").data("ranking",ranking);
   }
}

