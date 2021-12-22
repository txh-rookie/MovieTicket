package com.serookie.movie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.MovieVoQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevintam
 * @since 2021-12-12
 */
public interface MovieService extends IService<Movie> {

    Page<Movie> ConditionQuery(Integer current, Integer limit, MovieVoQuery movieVoQuery);
    Page<Movie> PageMovie(Integer current,Integer limit);
}
