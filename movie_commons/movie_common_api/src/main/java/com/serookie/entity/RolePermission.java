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
 * 角色权限
 * </p>
 *
 * @author kevintam
 * @since 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("movie_role_permission")
@ApiModel(value="RolePermission对象", description="角色权限")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    private String roleId;

    private String permissionId;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "电影创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
    @ApiModelProperty(value = "电影修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;



}
