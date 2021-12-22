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
 * @since 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="影片对象", description="")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电影编号")
    @TableId(value = "movie_id", type = IdType.ASSIGN_ID)
    private Long movieId;

    @ApiModelProperty(value = "电影名称（中文）")
    private String movieCnName;

    @ApiModelProperty(value = "电影名称（外语）")
    private String movieFgName;

    @ApiModelProperty(value = "电影演职人员")
    private String movieActor;

    @ApiModelProperty(value = "电影导演")
    private String movieDirector;

    @ApiModelProperty(value = "电影详情")
    private String movieDetail;

    @ApiModelProperty(value = "电影时长")
    private String movieDuration;

    @ApiModelProperty(value = "电影类型")
    private String movieType;

    @ApiModelProperty(value = "电影评分 默认为0")
    private Float movieScore;

    @ApiModelProperty(value = "电影票房 默认为0")
    @TableField("movie_boxOffice")
    private Float movieBoxoffice;

    @ApiModelProperty(value = "电影参评人数 默认为0")
    @TableField("movie_commentCount")
    private Long movieCommentcount;

    @ApiModelProperty(value = "电影上映时间")
    @TableField("movie_releaseDate")
    private Date movieReleasedate;

    @ApiModelProperty(value = "电影制片地区")
    private String movieCountry;

    @ApiModelProperty(value = "电影海报")
    private String moviePicture;

    @ApiModelProperty(value = "电影状态 默认为1  1：在线 0：下架")
    private Integer movieState;
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
