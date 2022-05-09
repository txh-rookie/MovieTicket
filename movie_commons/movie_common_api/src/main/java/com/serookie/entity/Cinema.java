package com.serookie.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.sql.Blob;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author kevintam
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_cinema")
@ApiModel(value="Cinema对象", description="影院对象")
public class Cinema implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "影院的主键id")
    @TableId(value = "cinema_id", type = IdType.AUTO)
    private String cinemaId;

    @ApiModelProperty(value = "电影的主键id,以,进行分割")
    @TableField(value = "cinema_film_id")
    private String cinemaFilmId;

    @ApiModelProperty(value = "影院的名称")
    private String cinemaName;

    @ApiModelProperty(value = "详细地区")
    @TableField(value = "cinema_address")
    private String cinemaAddress;

    @ApiModelProperty(value = "地区id")
    private Long cinemaAreaId;

    @ApiModelProperty(value = "影院评分")
    private Float cinemaRating;

    @ApiModelProperty(value = "影院的手机号")
    private String cinemaPhone;

    @ApiModelProperty(value = "影院的海报")
    private String cinemaPoster;

    @ApiModelProperty(value = "影院的服务id")
    private String cinemaServiceId;

    @ApiModelProperty(value = "影院的价格")
    private Double cinemaPrice;

    @ApiModelProperty(value = "影院的状态 0表示禁用 1表示启用")
    private Boolean cinemaStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "服务的集合")
    @TableField(exist = false)
    private List<String> services;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;
}
