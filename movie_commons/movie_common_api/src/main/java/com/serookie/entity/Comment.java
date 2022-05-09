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
 * 评论表
 * </p>
 *
 * @author kevintam
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_comment")
@ApiModel(value="Comment对象", description="评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论表的主键")
    @TableId(value = "comm_id", type = IdType.ASSIGN_ID)
    private String commId;

    @ApiModelProperty(value = "评论的内容")
    private String commContext;

    @ApiModelProperty(value = "评论的评分")
    private Integer commScore;

    @ApiModelProperty(value = "评论的电影id")
    private String filmId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("电影名")
    @TableField(exist = false)
    private String movieName;

    @ApiModelProperty(value = "用户的头像")
    @TableField(exist = false)
    private String headUrl;

    @ApiModelProperty(value = "评论时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "点赞数")
    private Integer giveNus;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;
}
