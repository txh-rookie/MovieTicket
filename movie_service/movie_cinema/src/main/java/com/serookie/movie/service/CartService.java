package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Cart;
import com.serookie.entity.Order;
import com.serookie.vo.OrderVo;

import java.util.List;

public interface CartService extends IService<Cart> {
    Page<Cart> CartPage(Integer current, Integer limit);

    Boolean insertCart(Cart cart);

    List<Cart> queryCartByUid(String uid);
}
