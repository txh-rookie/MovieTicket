package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;

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
@TableName("movie_order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单主键id")
    @TableId(value = "order_id", type = IdType.ASSIGN_ID)
    private String orderId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty("电影id")
    private String movieId;

    @ApiModelProperty("电影")
    @TableField(exist = false)
    private Movie movie;


    @ApiModelProperty(value = "所属场次编号")
    private String scheduleId;

    @ApiModelProperty(value = "电影票座位 （x排x座）")
    private String orderPosition;

    @ApiModelProperty(value = "订单状态 0:退票中 1:已支付 2:退票成功")
    private Integer orderState;

    @ApiModelProperty(value = "订单价格")
    private Double orderPrice;

    @ApiModelProperty(value = "支付状态 0 为未支付 1为已支付 2支付失败")
    private Integer playStatus;

    @ApiModelProperty(value = "支付的类型")
    private String playName;

    @ApiModelProperty(value = "订单支付时间")
    private Date orderTime;

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
