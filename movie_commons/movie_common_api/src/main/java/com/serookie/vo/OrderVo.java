package com.serookie.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("接受订单的字段")
public class OrderVo {
    private static final long serialVersionUID = 111111L;

    private String hid;

    private String uid;

    private String cartId;

    private String movieId;

    private String mail;

    private String seats;

    private Double price;
}
