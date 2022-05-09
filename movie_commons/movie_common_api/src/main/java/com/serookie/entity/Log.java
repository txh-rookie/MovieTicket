package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/1/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_log")
@ApiModel(value="日志对象", description="")
public class Log {

    @ApiModelProperty("主键id")
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户的id")
    private String userCode;

    @ApiModelProperty("ip地址")
    private String ip;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("模型")
    private String model;

    @ApiModelProperty(value = "电影创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @ApiModelProperty(value="返回结果")
    private String result;
}
