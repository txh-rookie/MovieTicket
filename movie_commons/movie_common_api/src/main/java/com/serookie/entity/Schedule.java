package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
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
@TableName("movie_schedule")
@ApiModel(value="Schedule对象", description="")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场次id")
    @TableId(value = "schedule_id", type = IdType.ASSIGN_ID)
    private String scheduleId;

    @ApiModelProperty(value = "所属放映厅编号")
    private String hallId;

    @ApiModelProperty(value = "所属电影编号")
    private String movieId;

    @TableField(exist = false)
    @ApiModelProperty(value = "电影名称")
    private String movieName;

    @ApiModelProperty("电影院编号")
    private String cinemaId;

    @TableField(exist = false)
    @ApiModelProperty("电影厅的名称")
    private String hallName;

    @ApiModelProperty("电影院名称")
    @TableField(exist = false)
    private String cinemaName;

    @ApiModelProperty(value = "电影放映时间")
    @TableField("schedule_startTime")
    private String scheduleStartTime;

    @ApiModelProperty(value = "电影散场时间")
    @TableField(exist = false)
    private String scheduleEndTime;

    @ApiModelProperty(value = "场次价格")
    private Double schedulePrice;

    @ApiModelProperty(value = "电影时长")
    @TableField(exist = false)
    private String movieDuration;

    @ApiModelProperty(value = "剩余座位数 默认=hall_capacity")
    private Integer scheduleRemain;

    @ApiModelProperty(value = "场次状态 默认1 1：上映中 0：下架")
    private Integer scheduleState;

    @ApiModelProperty(value = "影院")
    @TableField(exist = false)
    private Cinema cinema;

    @ApiModelProperty(value = "影厅")
    @TableField(exist = false)
    private Hall hall;

    @ApiModelProperty(value = "电影")
    @TableField(exist = false)
    private Movie movie;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
