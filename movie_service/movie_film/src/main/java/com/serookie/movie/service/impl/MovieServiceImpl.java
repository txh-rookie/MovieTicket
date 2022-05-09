package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.serookie.entity.Movie;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.vo.MovieVoQuery;
import com.serookie.movie.mapper.MovieMapper;
import com.serookie.movie.service.MovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.apache.logging.log4j.util.Strings;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Page<Movie> page = new Page<>(current, limit);
        Page<Movie> moviePage = movieMapper.selectPage(page, null);
        return moviePage;
    }

    @Override
    public List<Movie> hotMovie(Integer num) {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("movie_boxOffice").last("limit 10");
        List<Movie> movieList = movieMapper.selectList(queryWrapper);
        return movieList;
    }

    @Override
    public List<Movie> findByTypeAndCountry(String country, String type) {
        if(type.equals("全部") && country.equals("全部")){
            List<Movie> movieList = movieMapper.selectList(null);
            return movieList;
        }
        if(type.equals("全部")){
            String newCountry="%"+country+"%";
            QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("movie_country",country);
            List<Movie> movieList = movieMapper.selectList(queryWrapper);
            return movieList;
        }
        if(country.equals("全部")){
            String newType="%"+type+"%";
            QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("movie_type",type);
            List<Movie> movieList = movieMapper.selectList(queryWrapper);
            return movieList;
        }
        String newType="%"+type+"%";
        String newCountry="%"+country+"%";
        List<Movie> byTypeAndCountry = movieMapper.findByTypeAndCountry(newType, newCountry);
        return byTypeAndCountry;
    }

    @Override
    public List<Movie> searchByMovieName(String name) {
        if(StringUtils.isEmpty(name)){
            throw new CustomException(20001,"查询失败");
        }
        String movieName="%"+name+"%";
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("movie_cn_name",movieName);
        List<Movie> movieList = movieMapper.selectList(queryWrapper);
        return movieList;
    }

    @Override
    public Long boxOffice() {
        AtomicReference<Long> count= new AtomicReference<>(0L);
        List<Movie> movieList = movieMapper.selectList(null);
        movieList.forEach(elem->{
            count.updateAndGet(v -> v + elem.getMovieBoxoffice());
        });
        return count.get();
    }
    @Override
    public List<Movie> ranking() {
        QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Movie> movieBoxOffice = queryWrapper.orderByDesc("movie_boxOffice");
        List<Movie> movieList = movieMapper.selectList(movieBoxOffice);
        Stream<Movie> limit = movieList.stream().limit(5);
        List<Movie> collect = limit.collect(Collectors.toList());
        return collect;
    }
}
