package com.serookie.service;

import com.serookie.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author kevintam
 * @since 2022-04-28
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllPermission();

    void removeChildrenById(String id);

    void saveRolePermission(String roleId, String[] permissionId);
}
