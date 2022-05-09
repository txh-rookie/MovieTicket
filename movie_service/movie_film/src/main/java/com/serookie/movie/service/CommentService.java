package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    Page<Comment> PageComment(Integer current, Integer limit);


    List<Comment> findAll();

    Boolean give(String userId);

    List<Comment> findByContent(String content);

    List<Comment> listByFilmId(String filmId);
}
