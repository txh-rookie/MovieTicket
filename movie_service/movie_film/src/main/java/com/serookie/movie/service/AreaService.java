package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Area;

import java.util.List;

public interface AreaService extends IService<Area> {
    //已分级的形式展示
    List<Area> getArea();
}
