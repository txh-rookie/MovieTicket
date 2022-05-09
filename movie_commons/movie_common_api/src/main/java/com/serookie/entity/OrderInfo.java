package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
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
@TableName("movie_order_info")
@ApiModel(value="OrderInfo对象", description="")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单详情号")
      @TableId(value = "order_info_id", type = IdType.ASSIGN_ID)
    private String orderInfoId;

    @ApiModelProperty(value = "电影片的id")
    private String filmId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "电影的数量")
    private Integer num;

    @ApiModelProperty(value = "电影海报")
    private String filmPoster;

    @ApiModelProperty(value = "总金额")
    private Double totalAmount;

    @ApiModelProperty(value = "电影片单价")
    private BigDecimal price;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
