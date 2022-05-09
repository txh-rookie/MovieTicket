package com.serookie.movie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.serookie.entity.Cinema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CinemaMapper extends BaseMapper<Cinema> {
   List<Cinema> findByNameAndService(@Param("name") String name,@Param("service") String service);
}
