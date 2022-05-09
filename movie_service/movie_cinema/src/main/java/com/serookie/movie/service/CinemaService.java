package com.serookie.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Cinema;
import com.serookie.entity.Movie;
import com.serookie.vo.CinemaVoQuery;
import com.serookie.vo.MovieVoQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevintam
 * @since 2021-12-12
 */
public interface CinemaService extends IService<Cinema> {

    //添加影院
    boolean addCinema(Cinema cinema);
    //根据id查询影院
    Cinema getCinema(String cinemaId);
    //修改影院
    boolean updateCinema(Cinema cinema);
    //删除影院
    boolean delCinema(String cinemaId);
    //带有功能的分页
    Page<Cinema> ConditionQuery(Integer current, Integer limit, CinemaVoQuery cinemaVoQuery);
    //分页
    Page<Cinema> PageCinema(Integer current,Integer limit);

    List<Cinema> findAll();

    List<Cinema> searchByNameAndService(String name, String service);

    Cinema findByCinemaId(Long id);
}
