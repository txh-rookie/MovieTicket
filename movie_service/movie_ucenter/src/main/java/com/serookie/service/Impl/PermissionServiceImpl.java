package com.serookie.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.mapper.PermissionMapper;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.service.PermissionService;
import com.serookie.service.RolePermissionService;
import com.serookie.service.RoleService;
import com.serookie.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author kevintam
 * @since 2022-04-28
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;


    @Override
    public List<Permission> queryAllPermission() {
        //查询所有的菜单
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = permissionMapper.selectList(wrapper);
        //用递归进行循环查找二级和三级菜单
        List<Permission> permissionList = buildPermission(permissions);
        return permissionList;
    }

    /**
     * 递归循环删除
     *
     * @param id
     */
    @Override
    public void removeChildrenById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new CustomException(ResultEnum.NOT_PARAM_NULL);
        }
        List<String> ids = new ArrayList<>();
        ids.add(id);
        this.recursionRemove(id, ids);
        for (String str : ids
        ) {
            log.info("id的值为:" + str);
        }
        permissionMapper.deleteBatchIds(ids);
    }

    /**
     * 根据角色分配权限
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void saveRolePermission(String roleId, String[] permissionIds) {
        //初始化一个集合
        List<RolePermission> permissionList=new ArrayList<>();
        //遍历菜单表
        for (String permissionId : permissionIds) {
            System.out.println(permissionId);
            System.out.println(roleId);
            RolePermission permission = new RolePermission();
            permission.setPermissionId(permissionId);
            permission.setRoleId(roleId);
            permissionList.add(permission);
        }
        rolePermissionService.saveBatch(permissionList);
    }

    public void recursionRemove(String id, List<String> ids) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        queryWrapper.select("id");
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        permissionList.forEach(elem -> {
            ids.add(elem.getId());
            this.recursionRemove(elem.getId(), ids);
        });
    }

    public static List<Permission> buildPermission(List<Permission> permissions) {
        List<Permission> findPermission = new ArrayList<>();
        //找到订层菜单
        permissions.forEach(elem -> {
            if ("0".equals(elem.getPid())) {//找到顶层id
                //设置它的级数为1
                elem.setLevel(1);
                findPermission.add(selectPermissionChildren(elem, permissions));
            }
        });
        return findPermission;
    }

    public static Permission selectPermissionChildren(Permission permission, List<Permission> permissions) {
        permission.setChildren(new ArrayList<Permission>());//初始化
        permissions.forEach(elem -> {
            if (permission.getId().equals(elem.getPid())) {
                Integer level = permission.getLevel() + 1;
                elem.setLevel(level);
                permission.getChildren().add(selectPermissionChildren(elem, permissions));
            }
        });
        return permission;
    }
}
