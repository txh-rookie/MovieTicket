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
 * @createDate 2022/4/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_cart")
@ApiModel(value="cart对象", description="购物车主表")
public class Cart {

    private static final long serialVersionUID = 3L;

    @ApiModelProperty(value = "订单主键id")
    @TableId(value = "cart_id", type = IdType.ASSIGN_ID)
    private String cartId;

    private String hid;

    private String movieId;

    @TableField(exist = false)
    private Movie movie;

    @TableField(exist = false)
    private String movieStartTime;

    private String uid;

    private String mail;

    private String seats;

    private Double price;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "订单创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @ApiModelProperty(value = "订单的创建时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
