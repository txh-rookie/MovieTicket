package com.serookie.mapper;

import com.serookie.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author kevintam
 * @since 2022-04-28
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
