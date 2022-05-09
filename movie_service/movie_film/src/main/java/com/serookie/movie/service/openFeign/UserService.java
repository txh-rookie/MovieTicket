package com.serookie.movie.service.openFeign;

import com.serookie.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/10
 */
@FeignClient(value = "movie-user-center")
@Component
public interface UserService {

    @GetMapping("/user/{id}")
    public Result getById(@PathVariable("id") String id);
}
