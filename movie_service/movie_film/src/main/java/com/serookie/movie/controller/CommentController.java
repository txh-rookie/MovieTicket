package com.serookie.movie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.*;
import com.serookie.movie.service.CommentService;
import com.serookie.movie.service.MovieService;
import com.serookie.movie.service.openFeign.UserService;
import com.serookie.movie.utils.handler.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apiguardian.api.API;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/10
 */
@RestController
@CrossOrigin
@RefreshScope
@RequestMapping("/comm")
@Api(value = "电影评论接口", tags = {"电影评论接口"})
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private MovieService movieService;


    @GetMapping("/all")
    @ApiOperation("查询所有的评论")
    public Result all(){
       List<Comment> lists= commentService.findAll();
       return Result.ok().message("查询成功").data("comments",lists);
    }
    @GetMapping("/list/{current}/{limit}")
    @ApiOperation("评论的数据分页")
    public Result PageMovie(@ApiParam("当前页参数") @PathVariable("current") Integer current,
                            @ApiParam("每页的记录数")@PathVariable("limit") Integer limit){
//        Result area = areaService.getArea(address);
        Page<Comment> cinemaPage = commentService.PageComment(current, limit);
        return Result.ok().message("查询成功").data("total",cinemaPage.getTotal()).data("rows",cinemaPage);
    }
    @PostMapping("/add")
    @ApiOperation("添加评论")
    public Result addComment(@RequestBody Comment comment){
        if(StringUtils.isEmpty(comment)){
            throw new CustomException(20001,"数据不能为空");
        }
        if(StringUtils.isEmpty(comment.getFilmId())){
            throw new CustomException(2001,"电影id不能为空");
        }
        Movie movie = movieService.getById(comment.getFilmId());
        Long movieCommentCount = movie.getMovieCommentcount()+1;
        movie.setMovieCommentcount(movieCommentCount);
        movieService.updateById(movie);
        boolean save = commentService.save(comment);
        if(save){
            return Result.ok().message("查询成功");
        }else {
            return Result.error().message("查询失败");
        }
    }
    @GetMapping("/{userId}")
    @ApiOperation("点赞")
    public Result giveComment(@PathVariable("userId") String userId){
       Boolean temp= commentService.give(userId);
        if(temp){
            return Result.ok().message("点赞成功");
        }else {
            return Result.error().message("点赞失败");
        }
    }
    @GetMapping("/search")
    @ApiOperation("搜索")
    public Result searchComment(@RequestParam("content") String content){
       List<Comment> comments=commentService.findByContent(content);
       return Result.ok().message("查询成功").data("rows",comments);
    }
    @DeleteMapping("/{commId}")
    @ApiOperation("根据id进行删除")
    public Result delComment(@PathVariable("commId") String commId){
        boolean temp = commentService.removeById(commId);
        if(temp){
            return Result.ok().message("删除成功");
        }else {
            return Result.error().message("删除失败");
        }
    }
    @GetMapping("/list")
    @ApiOperation("根据film_id查询评论")
    public Result listComments(@RequestParam("filmId") String filmId){
        List<Comment> comments=commentService.listByFilmId(filmId);
        return Result.ok().message("查询成功").data("comments",comments);
    }
}
