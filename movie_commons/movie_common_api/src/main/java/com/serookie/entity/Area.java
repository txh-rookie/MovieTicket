package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2021/12/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_area")
@ApiModel(value="area对象", description="地区主表")
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    //雪花算法生成id
    @TableId(value = "area_id", type = IdType.ASSIGN_ID)
    private Long areaId;
    @ApiModelProperty("地区名称")
    private String areaName;
    @ApiModelProperty("地区的层级 1为市区 2为地区")
    private Integer areaLevel;
    @ApiModelProperty("父级id")
    private Integer parentCid;
    @ApiModelProperty("是否显示 0 为显示 1为不显示")
    private Integer showStatus;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
    @ApiModelProperty(value = "电影修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    @ApiModelProperty("存储两级地区数据")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Area> areaList;
}
