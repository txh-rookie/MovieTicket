package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.awt.*;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 演员表
 * </p>
 *
 * @author kevintam
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_active")
@ApiModel(value="Active对象", description="活动表")
public class Active implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "演员的id主键")
    @TableId(value = "active_id", type = IdType.ASSIGN_ID)
    private String activeId;

    private String content;
    private String endTime;
    private String startTime;
    private String uid;
}
