package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Cinema;
import com.serookie.entity.Hall;
import com.serookie.movie.mapper.CinemaMapper;
import com.serookie.movie.mapper.HallMapper;
import com.serookie.movie.service.HallService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/1/1
 */
@Service
public class HallServiceImpl extends ServiceImpl<HallMapper, Hall> implements HallService {

    @Resource
    private HallMapper hallMapper;
    @Resource
    private CinemaMapper cinemaMapper;


    @Override
    public Page<Hall> HallPage(Integer current, Integer limit) {
        Page<Hall> hallPage = new Page<>(current,limit);
        Page<Hall> selectPage = hallMapper.selectPage(hallPage, null);
        List<Hall> records = selectPage.getRecords();
        records.forEach(val->{
            Cinema cinema = cinemaMapper.selectById(val.getCinemaId());
            val.setCinemaName(cinema.getCinemaName());
        });
        return selectPage;
    }

    @Override
    public List<Hall> getCinemaIdByHall(Long cinemaId) {
        QueryWrapper<Hall> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cinema_id",cinemaId);
        List<Hall> halls = hallMapper.selectList(queryWrapper);
        return halls;
    }
}
