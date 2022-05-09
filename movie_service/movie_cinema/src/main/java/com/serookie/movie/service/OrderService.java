package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Hall;
import com.serookie.entity.Order;
import com.serookie.vo.OrderVo;

import java.util.List;

public interface OrderService extends IService<Order> {
    Page<Order> OrderPage(Integer current, Integer limit);

    Boolean insertOrder(OrderVo orderVo);

    List<Order> findByMovieId(String movieId);

    List<Order> findByUid(String uid);
}
