package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Movie;
import com.serookie.entity.MovieVoQuery;
import com.serookie.movie.mapper.MovieMapper;
import com.serookie.movie.service.MovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevintam
 * @since 2021-12-12
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Page<Movie> ConditionQuery(Integer current, Integer limit, MovieVoQuery movieVoQuery) {
        Page<Movie> page = new Page<>(current, limit);
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        String cnName = movieVoQuery.getMovieCnName();
        String fgName = movieVoQuery.getMovieFgName();
        String movieType = movieVoQuery.getMovieType();
        String begin = movieVoQuery.getBegin();
        String end = movieVoQuery.getEnd();
        if (!Strings.isEmpty(cnName)) {
            queryWrapper.like("movie_cn_name", cnName);//通过中文名称模糊查询
        }
        if (!Strings.isEmpty(fgName)) {
            queryWrapper.like("movie_fg_name", fgName);//通过中文名称进行查询
        }
        if (!Strings.isEmpty(movieType)) {
            queryWrapper.like("movie_type", movieType);
        }
        if (!Strings.isEmpty(begin)) {
            queryWrapper.ge("movie_releaseDate", begin);
        }
        if (!Strings.isEmpty(end)) {
            queryWrapper.le("movie_releaseDate", end);
        }
        Page<Movie> page1 = movieMapper.selectPage(page, queryWrapper);
        return page1;
    }

    @Override
    public Page<Movie> PageMovie(Integer current, Integer limit) {
        //redis的key为PageMovie
        redisTemplate.opsForValue().set("movie",current,1, TimeUnit.DAYS);
        Page<Movie> page = new Page<>(current, limit);
        Page<Movie> moviePage = movieMapper.selectPage(page, null);
        return moviePage;
    }
}
