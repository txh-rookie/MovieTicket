package com.serookie.movie.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.movie.mapper.ScheduleMapper;
import com.serookie.movie.service.CinemaService;
import com.serookie.movie.service.HallService;
import com.serookie.movie.service.OpnFeign.MovieService;
import com.serookie.movie.service.ScheduleService;
import com.serookie.movie.utils.handler.CustomException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/1/1
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private MovieService movieService;

    @Resource
    private CinemaService cinemaService;

    @Resource
    private HallService hallService;

    @Override
    public Page<Schedule> page(Integer current, Integer limit) {
        Page<Schedule> page = new Page<>(current, limit);
        Page<Schedule> schedulePage = scheduleMapper.selectPage(page, null);
        List<Schedule> records = schedulePage.getRecords();
        records.forEach(val -> {
            Result movie = movieService.getMovie(val.getMovieId());
            String movie1 = JSON.toJSONString(movie.getData().get("movie"));
            Movie movie2 = JSON.parseObject(movie1, Movie.class);
            val.setMovieName(movie2.getMovieCnName());
            Cinema cinema = cinemaService.getCinema(val.getCinemaId());
            val.setCinemaName(cinema.getCinemaName());
            System.out.println(val.getHallId());
            if(StringUtils.isEmpty(val.getHallId())){
                throw new CustomException(ResultEnum.NOT_PARAM_NULL);
            }
            Hall hall = hallService.getById(val.getHallId());
            val.setHallName(hall.getHallName());
            val.setMovieDuration(movie2.getMovieDuration());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = simpleDateFormat.parse(val.getScheduleStartTime());
                Calendar instance = Calendar.getInstance();
                instance.setTime(date);
                instance.add(Calendar.MINUTE,Integer.parseInt(val.getMovieDuration()));
                String format = simpleDateFormat.format(instance.getTime());
                val.setScheduleEndTime(format);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        return schedulePage;
    }

    @Override
    public List<Schedule> findByMovieId(String movieId) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("movie_id",movieId);
        List<Schedule> schedules = scheduleMapper.selectList(queryWrapper);
        if(StringUtils.isEmpty(schedules)){
            throw new CustomException(20001,"查询失败");
        }
        List<Cinema> cinemas = cinemaService.findAll();
        schedules.forEach(elem->{
//            String cinemaId = elem.getCinemaId();
//            List<Cinema> cinemas = cinemaService.findAll();
            Optional<Cinema> first = cinemas.stream().filter(val -> {
                return elem.getCinemaId().equals(val.getCinemaId());
            }).findFirst();
//            System.out.println(first.get());
//            Cinema cinema = first.get();
//            System.out.println(cinema);
            elem.setCinema(first.get());
        });
        return schedules;
    }

    @Override
    public List<Schedule> findByCinemaId(String cinemaId) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cinema_id",cinemaId);
        List<Schedule> schedules = scheduleMapper.selectList(queryWrapper);
        if(StringUtils.isEmpty(schedules)){
            throw new CustomException(20001,"查询失败");
        }
        schedules.forEach(elem->{
            Result movie = movieService.getMovie(elem.getMovieId());
            String movie1 = JSON.toJSONString(movie.getData().get("movie"));
            Movie movie2 = JSON.parseObject(movie1, Movie.class);
            elem.setMovie(movie2);
            Hall hall = hallService.getById(elem.getHallId());
            elem.setHall(hall);
        });
        return schedules;
    }

    @Override
    public Schedule findById(String id) {
        Schedule schedule = scheduleMapper.selectById(id);
        String hallId = schedule.getHallId();
        Hall hall = hallService.getById(hallId);
        schedule.setHall(hall);
        String movieId = schedule.getMovieId();
        Result movie = movieService.getMovie(movieId);
        String movie1 = JSON.toJSONString(movie.getData().get("movie"));
        Movie movie2 = JSON.parseObject(movie1, Movie.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = simpleDateFormat.parse(schedule.getScheduleStartTime());
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            instance.add(Calendar.MINUTE,Integer.parseInt(movie2.getMovieDuration()));
            String format = simpleDateFormat.format(instance.getTime());
            schedule.setScheduleEndTime(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        schedule.setMovie(movie2);
        return schedule;
    }

}
