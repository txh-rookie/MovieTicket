package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Movie;
import com.serookie.entity.Swiper;

import java.util.List;

public interface SwiperService extends IService<Swiper> {
    Page<Swiper> PageMovie(Integer current, Integer limit);

    Boolean insertSwiper(Swiper swiper);

    Boolean delSwiper(String swiperId);

    List<Swiper> all();
}
