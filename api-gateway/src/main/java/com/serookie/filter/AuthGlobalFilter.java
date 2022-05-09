package com.serookie.filter;

import com.alibaba.fastjson.JSONObject;
import com.serookie.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/28
 * 全局过滤相当于springboot的拦截器
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    //过滤
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> token = request.getHeaders().get("token");
        if (null==token){
            ServerHttpResponse response = exchange.getResponse();
            log.info("用户名为空，找不到该用户");
            return out(response);
            // 设置状态码
//            exchange.getResponse().setStatusCode(HttpStatus.);
        }
        // 返回
        log.info("进入拦截器");
        return chain.filter(exchange);
    }

    /**
     * 加载过滤器的顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
    private Mono<Void> out(ServerHttpResponse response){
        JSONObject json = new JSONObject();
        json.put("code",20005);
        json.put("message","鉴权失败");
        byte[] bits = json.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
