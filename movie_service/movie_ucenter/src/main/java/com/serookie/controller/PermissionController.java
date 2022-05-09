package com.serookie.controller;

import com.serookie.entity.Permission;
import com.serookie.entity.Result;
import com.serookie.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/permission")
@CrossOrigin
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public Result indexAllPermission() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok().data("children", list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        permissionService.removeChildrenById(id);
        return Result.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam("roleId") String roleId,@RequestParam("permissionId") String[] permissionId) {
        System.out.println(roleId);
        permissionService.saveRolePermission(roleId,permissionId);
        return Result.ok();
    }
//    @ApiOperation(value = "根据角色获取菜单")
//    @GetMapping("toAssign/{roleId}")
//    public R toAssign(@PathVariable String roleId) {
//        List<Permission> list = permissionService.selectAllMenu(roleId);
//        return R.ok().data("children", list);
//    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok();
    }

}

