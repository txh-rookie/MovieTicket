package com.serookie.movie.service.OpnFeign;

import com.serookie.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "movie-msm-provider")
@Component
public interface MsmService {
    @GetMapping("/msm/send/{email}/{message}/{orderId}")
    public Result sendMail(@PathVariable("email") String email, @PathVariable("message") String message, @PathVariable("orderId") String orderId);
}
