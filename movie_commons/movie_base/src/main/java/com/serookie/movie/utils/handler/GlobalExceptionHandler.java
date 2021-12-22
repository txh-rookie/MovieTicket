package com.serookie.movie.utils.handler;

import com.serookie.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常....");
    }
    //单一异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result ArithmeticException(Exception e){
        return Result.error().message(e.getMessage());
    }
    //自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customException(CustomException e){
       e.printStackTrace();
       return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
