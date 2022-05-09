package com.serookie.service;

import com.serookie.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kevintam
 * @since 2022-04-30
 */
public interface RoleService extends IService<Role> {
    List<Role> selectRoleByUserId(String id);
    void saveUserRole(String userId, String[] roleIds);
    public Map<String, Object> findRoleByUserId(String userId);
}
