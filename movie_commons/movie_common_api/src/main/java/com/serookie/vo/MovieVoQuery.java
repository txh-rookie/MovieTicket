package com.serookie.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("条件的查询的封装")
public class MovieVoQuery implements Serializable {
    private static final long serialVersionUID = 2L;

    @ApiModelProperty(value = "电影名称（中文）")
    private String movieCnName;
    @ApiModelProperty(value = "电影名称（外语）")
    private String movieFgName;
    @ApiModelProperty(value = "电影类型")
    private String movieType;
    @ApiModelProperty(value="电影的开始时间",example = "2021-12-2 10:10:10")
    private String begin;
    @ApiModelProperty(value = "电影的截止时间",example = "2021-12-12 12:12:12")
    private String end;
}
