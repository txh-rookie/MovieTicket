package com.serookie.mapper;

import com.serookie.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 * 角色权限 Mapper 接口
 * </p>
 *
 * @author kevintam
 * @since 2022-04-30
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}
