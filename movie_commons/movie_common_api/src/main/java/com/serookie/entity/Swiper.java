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
 * @createDate 2022/3/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_swiper")
@ApiModel(value="影片对象", description="")
public class Swiper {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "swiper_id", type = IdType.ASSIGN_ID)
    private String swiperId;
    @ApiModelProperty(value = "轮播图地址")
    private String swiperSrc;
    @ApiModelProperty(value = "轮播图的状态,1为上架，0为下架")
    private Integer swiperStatus;

    @ApiModelProperty(value = "对应电影id")
    private String filmId;
    @ApiModelProperty(value = "轮播图名称")
    private String swiperName;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;
    @ApiModelProperty(value = "电影创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
    @ApiModelProperty(value = "电影修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
