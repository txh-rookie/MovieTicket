package com.serookie.movie.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.movie.mapper.HallMapper;
import com.serookie.movie.mapper.OrderMapper;
import com.serookie.movie.service.CartService;
import com.serookie.movie.service.HallService;
import com.serookie.movie.service.OpnFeign.MovieService;
import com.serookie.movie.service.OpnFeign.MsmService;
import com.serookie.movie.service.OrderService;
import com.serookie.movie.service.ScheduleService;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.vo.OrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private static ReentrantLock lock=new ReentrantLock();

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private CartService cartService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private MovieService movieService;

    @Resource
    private MsmService msmService;

    @Override
    public Page<Order> OrderPage(Integer current, Integer limit) {
        Page<Order> page = new Page<>(current,limit);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        Page<Order> orderPage = orderMapper.selectPage(page, queryWrapper);
        return orderPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT
    ,rollbackFor = CustomException.class,timeout = 50)
    public Boolean insertOrder(OrderVo vo) {
        if(StringUtils.isEmpty(vo)){
            throw new CustomException(2001,"添加失败");
        }
        lock.lock();
        try{
            Order order = new Order();
            order.setUserId(vo.getUid());
            order.setScheduleId(vo.getHid());
            order.setOrderTime(new Date());
            order.setPlayName("微信支付");
            order.setOrderPosition(vo.getSeats());
            order.setPlayStatus(1);//支付成功
            order.setOrderState(2);//已完成订单
            order.setOrderPrice(vo.getPrice());
            order.setMovieId(vo.getMovieId());
            if(orderMapper.insert(order)>0){
                boolean flag = cartService.removeById(vo.getCartId());
                Schedule schedule = scheduleService.getById(vo.getHid());
                Result result = msmService.sendMail(vo.getMail(), schedule.getScheduleStartTime(), order.getOrderId());
                Result movie = movieService.getMovie(vo.getMovieId());
                String movie1 = JSON.toJSONString(movie.getData().get("movie"));
                Movie movie2 = JSON.parseObject(movie1, Movie.class);
                Long movieBoxOffice = movie2.getMovieBoxoffice()+1;
                movie2.setMovieBoxoffice(movieBoxOffice);
                movieService.updateFilm(movie2);
                if(flag){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (CustomException e){
            e.printStackTrace();
        }finally {
          lock.unlock();
        }
        return false;
    }

    @Override
    public List<Order> findByMovieId(String movieId) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("movie_id",movieId);
        List<Order> orders = orderMapper.selectList(orderQueryWrapper);
        return orders;
    }

    @Override
    public List<Order> findByUid(String uid) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uid);
        List<Order> orders = orderMapper.selectList(queryWrapper);
        if(StringUtils.isEmpty(orders)){
            throw new CustomException(2001,"订单为空,查询失败");
        }
        orders.forEach(elem->{
            Result result = movieService.getMovie(elem.getMovieId());
            String movie = JSON.toJSONString(result.getData().get("movie"));
            Movie movie1 = JSON.parseObject(movie, Movie.class);
            elem.setMovie(movie1);
        });
        return orders;
    }
}
