package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Movie;
import com.serookie.entity.Swiper;
import com.serookie.movie.mapper.MovieMapper;
import com.serookie.movie.mapper.SwiperMapper;
import com.serookie.movie.service.MovieService;
import com.serookie.movie.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/3/14
 */
@Service
public class SwiperServiceImpl extends ServiceImpl<SwiperMapper, Swiper> implements SwiperService {
      @Resource
      private SwiperMapper swiperMapper;

      @Resource
      private MovieMapper movieMapper;

    /**
     * 轮播图的分页实现
     * @param current
     * @param limit
     * @return
     */
    @Override
    public Page<Swiper> PageMovie(Integer current, Integer limit) {
        Page<Swiper> page = new Page<>(current, limit);
        Page<Swiper> swiperPage = swiperMapper.selectPage(page, null);
        return swiperPage;
    }

    @Override
    public Boolean insertSwiper(Swiper swiper) {
        String filmId = swiper.getFilmId();
        Movie movie = movieMapper.selectById(filmId);
        swiper.setSwiperName(movie.getMovieCnName());
        int insert = swiperMapper.insert(swiper);
        return insert>0;
    }

    @Override
    public Boolean delSwiper(String swiperId) {
        int flag = swiperMapper.deleteById(swiperId);
        return flag>0;
    }

    @Override
    public List<Swiper> all() {
        List<Swiper> swiperList = swiperMapper.selectList(null);
        List<Swiper> collect = swiperList.stream().filter(swiper ->
            swiper.getSwiperStatus() == 1
        ).collect(Collectors.toList());
        return collect;
    }
}
