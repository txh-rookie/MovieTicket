package com.serookie.movie.service.OpnFeign;

import com.serookie.entity.Movie;
import com.serookie.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "movie-film-provider")
@Component
public interface MovieService {
    @GetMapping("/movie/{id}")
    public Result getMovie(@PathVariable("id") String id);
    @PutMapping("/movie/film")
    public Result updateFilm(@RequestBody Movie movie);
}
