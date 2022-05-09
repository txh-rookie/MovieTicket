package com.serookie.movie.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.movie.mapper.CartMapper;
import com.serookie.movie.service.CartService;
import com.serookie.movie.service.HallService;
import com.serookie.movie.service.OpnFeign.MovieService;
import com.serookie.movie.service.ScheduleService;
import com.serookie.movie.utils.handler.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/19
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper,Cart> implements CartService {

    public static final ReentrantLock lock=new ReentrantLock();

    @Resource
    private CartMapper cartMapper;

    @Resource
    private MovieService movieService;

    @Resource
    private ScheduleService scheduleService;

    @Override
    public Page<Cart> CartPage(Integer current, Integer limit) {
        Page<Cart> pageCarts = new Page<>(current,limit);
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        Page<Cart> page =cartMapper.selectPage(pageCarts, queryWrapper);
        return page;
    }

    @Override
    @Transactional(rollbackFor = CustomException.class)
    public Boolean insertCart(Cart cart) {
        if(StringUtils.isEmpty(cart)){
            throw new CustomException(2001,"添加购物车失败");
        }
        lock.lock();
        try{
          return cartMapper.insert(cart)>0;
        }catch(CustomException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return false;
    }

    @Override
    public List<Cart> queryCartByUid(String uid) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        carts.forEach(elem->{
            String movieId = elem.getMovieId();
            Result result = movieService.getMovie(movieId);
            String jsonMovie = JSON.toJSONString(result.getData().get("movie"));
            Movie movie = JSON.parseObject(jsonMovie, Movie.class);
            elem.setMovie(movie);
            String hid = elem.getHid();
            Schedule schedule = scheduleService.getById(hid);
            elem.setMovieStartTime(schedule.getScheduleStartTime());
        });
        return carts;
    }
}
