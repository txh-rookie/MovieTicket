package com.serookie.movie.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.movie.mapper.CommentMapper;
import com.serookie.movie.mapper.MovieMapper;
import com.serookie.movie.service.CommentService;
import com.serookie.movie.service.openFeign.UserService;
import com.serookie.movie.utils.handler.CustomException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserService userService;

    @Resource
    private MovieMapper movieMapper;

    @Override
    public Page<Comment> PageComment(Integer current, Integer limit) {
        Page<Comment> commentPage = new Page<Comment>(current,limit);
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        Page<Comment> page = commentMapper.selectPage(commentPage, queryWrapper);
        List<Comment> records = page.getRecords();
        if (records.isEmpty()) {
            throw new CustomException(20001,"查询失败");
        }
        records.forEach((elem)->{
            String filmId = elem.getFilmId();
            String id = elem.getUserId();
            System.out.println(id);
            Result result = userService.getById(id);
            String user = JSON.toJSONString(result.getData().get("user"));
            User newUser = JSON.parseObject(user, User.class);
            elem.setUsername(newUser.getUsername());
            elem.setHeadUrl(newUser.getHeadUrl());
            Movie movie = movieMapper.selectById(filmId);
            elem.setMovieName(movie.getMovieCnName());
        });
        return page;
    }

    @Override
    public List<Comment> findAll() {
        return commentMapper.selectList(null);
    }

    @Override
    public Boolean give(String userId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        Comment comment = commentMapper.selectOne(queryWrapper);
        Integer giveNus= comment.getGiveNus()+ 1;
        comment.setGiveNus(giveNus);
        int insert = commentMapper.insert(comment);
        return insert>0;
    }

    @Override
    public List<Comment> findByContent(String content) {
        if(StringUtils.isEmpty(content)){
            throw new CustomException(20001,"查询失败");
        }
        String newContent="%"+content+"%";
        List<Comment> comments= commentMapper.findByContent(newContent);
        if(comments.isEmpty()){
            return comments;
        }
        comments.forEach(elem->{
            Result res = userService.getById(elem.getUserId());
            String user = JSON.toJSONString(res.getData().get("user"));
            User user1 = JSON.parseObject(user, User.class);
            elem.setUsername(user1.getUsername());
            Movie movie = movieMapper.selectById(elem.getFilmId());
            elem.setHeadUrl(user1.getHeadUrl());
            elem.setMovieName(movie.getMovieCnName());
        });
        return comments;
    }

    @Override
    public List<Comment> listByFilmId(String filmId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("film_id",filmId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        comments.forEach(elem->{
            Result res = userService.getById(elem.getUserId());
            String user = JSON.toJSONString(res.getData().get("user"));
            User user1 = JSON.parseObject(user, User.class);
            elem.setUsername(user1.getUsername());
            Movie movie = movieMapper.selectById(elem.getFilmId());
            elem.setHeadUrl(user1.getHeadUrl());
            elem.setMovieName(movie.getMovieCnName());
        });
        return comments;
    }
}
