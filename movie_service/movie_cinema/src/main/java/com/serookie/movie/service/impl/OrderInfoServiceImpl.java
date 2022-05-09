package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Order;
import com.serookie.entity.OrderInfo;
import com.serookie.movie.mapper.OrderInfoMapper;
import com.serookie.movie.mapper.OrderMapper;
import com.serookie.movie.service.CartService;
import com.serookie.movie.service.OrderInfoService;
import com.serookie.movie.service.OrderService;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.vo.OrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/19
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

}
