package com.serookie.movie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.serookie.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/10
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> findByContent(@Param("content") String content);
}
