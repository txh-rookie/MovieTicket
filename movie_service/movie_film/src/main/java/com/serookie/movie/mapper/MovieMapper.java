package com.serookie.movie.mapper;

//import com.serookie.entity.Film;
import com.serookie.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kevintam
 * @since 2021-12-12
 */
public interface MovieMapper extends BaseMapper<Movie> {
    List<Movie> findByTypeAndCountry(@Param("type") String type,@Param("country") String country);
}
