package com.serookie.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Cinema;
import com.serookie.entity.Movie;
import com.serookie.movie.mapper.CinemaMapper;
import com.serookie.movie.mapper.ServiceMapper;
import com.serookie.movie.service.CinemaService;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.vo.CinemaVoQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/25
 */
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema> implements CinemaService{

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private ServiceMapper serviceMapper;

    @Override
    public boolean addCinema(Cinema cinema) {
        int insert = cinemaMapper.insert(cinema);
        return insert>0;
    }

    @Override
    public Cinema getCinema(String cinemaId) {
        return cinemaMapper.selectById(cinemaId);
    }

    @Override
    public boolean updateCinema(Cinema cinema) {
        return cinemaMapper.updateById(cinema)>0;
    }

    @Override
    public boolean delCinema(String cinemaId) {
        return cinemaMapper.deleteById(cinemaId)>0;
    }

    @Override
    public Page<Cinema> ConditionQuery(Integer current, Integer limit, CinemaVoQuery cinemaVoQuery) {
        Page<Cinema> cinemaPage = new Page<Cinema>(current,limit);
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        Page<Cinema> page = cinemaMapper.selectPage(cinemaPage, queryWrapper);
        return page;
    }

    @Override
    public Page<Cinema> PageCinema(Integer current, Integer limit) {
        Page<Cinema> cinemaPage = new Page<Cinema>(current,limit);
//        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        Page<Cinema> page = cinemaMapper.selectPage(cinemaPage, null);
        return page;
    }

    @Override
    public List<Cinema> findAll() {
        List<Cinema> cinemas = cinemaMapper.selectList(null);
        cinemas.forEach((elem)->{
            String cinemaServiceId = elem.getCinemaServiceId();
            String[] split = cinemaServiceId.split("#");
            List<String> list = new ArrayList();
            for (int i = 0; i <split.length ; i++) {
                com.serookie.entity.Service service = serviceMapper.selectById(split[i]);
                if(service==null){
                    list.add("空");
                }else {
                    list.add(service.getServiceTag());
                }
            }
            elem.setServices(list);
        });
        return cinemas;
    }

    @Override
    public List<Cinema> searchByNameAndService(String name, String service) {
        if(StringUtils.isEmpty(service)){
           throw new CustomException(20001,"查询条件不能为空");
        }
        List<Cinema> all = findAll();
        if(name.equals("全部") && service.equals("全部")){
            return all;
        }
        if(service.equals("全部")){
            List<Cinema> cinemas = all.stream().filter(elem -> {
                return name.equals(elem.getCinemaName());
            }).collect(Collectors.toList());
            return cinemas;
        }
        QueryWrapper<com.serookie.entity.Service> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("service_tag",service);
        com.serookie.entity.Service selectOne = serviceMapper.selectOne(queryWrapper);
       String serviceId= "%"+selectOne.getServiceId()+"%";
        if(name.equals("全部")){
            QueryWrapper<Cinema> wrapper = new QueryWrapper<>();
            wrapper.like("cinema_service_id",serviceId);
            List<Cinema> cinemas = cinemaMapper.selectList(wrapper);
            cinemas.forEach(elem->{
                String cinemaServiceId = elem.getCinemaServiceId();
                String[] split = cinemaServiceId.split("#");
                List<String> list = new ArrayList();
                for (int i = 0; i <split.length ; i++) {
                    com.serookie.entity.Service services = serviceMapper.selectById(split[i]);
                    if(services==null){
                        list.add("空");
                    }else {
                        list.add(services.getServiceTag());
                    }
                }
                elem.setServices(list);
            });
            return cinemas;
        }
        List<Cinema> cinemas = cinemaMapper.findByNameAndService(name, serviceId);
        cinemas.forEach(elem->{
            String cinemaServiceId = elem.getCinemaServiceId();
            String[] split = cinemaServiceId.split("#");
            List<String> list = new ArrayList();
            for (int i = 0; i <split.length ; i++) {
                com.serookie.entity.Service services = serviceMapper.selectById(split[i]);
                if(services==null){
                    list.add("空");
                }else {
                    list.add(services.getServiceTag());
                }
            }
            elem.setServices(list);
        });
        return cinemas;
    }

    @Override
    public Cinema findByCinemaId(Long id) {
        Cinema cinema = cinemaMapper.selectById(id);
        if(StringUtils.isEmpty(cinema)){
            throw new CustomException(2001,"查询失败");
        }
        String cinemaServiceId = cinema.getCinemaServiceId();
        String[] split = cinemaServiceId.split("#");
        List<String> lists=new ArrayList<>();
        for (int i = 0; i <split.length ; i++) {
            com.serookie.entity.Service service = serviceMapper.selectById(split[i]);
            if(service==null){
                lists.add("空");
            }else {
                lists.add(service.getServiceTag());
            }

        }
        cinema.setServices(lists);
        return cinema;
    }
}
