package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_service")
@ApiModel(value="Schedule对象", description="")
public class Service {

    @ApiModelProperty(value = "服务id")
    @TableId(value = "service_id", type = IdType.ASSIGN_ID)
    private String serviceId;

    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    @ApiModelProperty(value = "服务的标签")
    private String serviceTag;
}
