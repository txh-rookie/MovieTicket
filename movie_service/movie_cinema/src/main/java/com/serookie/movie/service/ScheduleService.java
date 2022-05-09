package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Schedule;

import java.util.List;

public interface ScheduleService extends IService<Schedule> {
    //数据分页
    Page<Schedule> page(Integer current,Integer limit);

    List<Schedule> findByMovieId(String movieId);

    List<Schedule> findByCinemaId(String cinemaId);

    Schedule findById(String id);

}
