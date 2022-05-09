package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Hall;

import java.util.List;

public interface HallService extends IService<Hall> {
    Page<Hall> HallPage(Integer current, Integer limit);
    List<Hall> getCinemaIdByHall(Long cinemaId);
}
