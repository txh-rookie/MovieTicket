package com.serookie.movie.utils.handler;

import com.serookie.entity.ResultEnum;
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

    /**
     * 错误信息
     * @param message
     */
    public CustomException(String message){
        this.Msg=message;
    }
    public CustomException(Integer code,String message,Throwable throwable){
        super(throwable);
        this.code=code;
        this.Msg=message;
    }
    public CustomException(ResultEnum resultEnum,Throwable throwable){
        super(throwable);
        this.code=resultEnum.getCode();
        this.Msg=resultEnum.getMessage();
    }
    public CustomException(ResultEnum resultEnum){
        this.code=resultEnum.getCode();
        this.Msg=resultEnum.getMessage();
    }
}
