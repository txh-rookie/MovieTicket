package com.serookie.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/7
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_user")
@ApiModel(value = "影片对象", description = "")
public class User {
    private static final long serialVersionUID = 5L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户性别")
    private Integer userSex;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty("用户的邮箱")
    private String email;

    @ApiModelProperty(value = "用户的手机")
    private String userPhone;

    @ApiModelProperty(value = "用户的地址")
    private String address;

    @ApiModelProperty(value = "用户的头像")
    private String headUrl;

    @ApiModelProperty(value = "用户的状态")
    private Boolean userStatus;

    @ApiModelProperty(value = "用户的个人介绍")
    private String biography;

    @ApiModelProperty(value = "用户的收藏的电影id")
    private String collection;

    @ApiModelProperty(value = "个人爱好")
    private Integer lifeState;

    @ApiModelProperty("用户权限菜单")
    @TableField(exist = false)
    private List<Permission> permissionList;

    @ApiModelProperty("用户的权限")
    @TableField(exist = false)
    private String roleName;

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
