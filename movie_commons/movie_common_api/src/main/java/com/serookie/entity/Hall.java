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
@TableName("movie_hall")
@ApiModel(value="Hall对象", description="影厅对象")
public class Hall implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "放映厅的id")
    @TableId(value = "hall_id", type = IdType.ASSIGN_ID)
    private String hallId;

    @ApiModelProperty(value = "放映厅的名称")
    private String hallName;

    @ApiModelProperty(value = "放映厅的列数")
    private Integer hallColumn;

    @ApiModelProperty("影院名称")
    @TableField(exist = false)
    private String cinemaName;

    @ApiModelProperty(value = "放映厅的列数")
    private Integer HallRow;

    @ApiModelProperty(value = "放映厅容量 默认为144 12x12")
    private Integer hallCapacity;

    @ApiModelProperty(value = "所属影院编号")
    private String cinemaId;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
