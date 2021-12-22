package com.serookie.movie.utils.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private Integer code;
    private String Msg;

}
