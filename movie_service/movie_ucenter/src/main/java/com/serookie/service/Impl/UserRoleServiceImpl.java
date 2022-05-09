package com.serookie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.UserRole;
import com.serookie.mapper.UserRoleMapper;
import com.serookie.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员角色管理表表 服务实现类
 * </p>
 *
 * @author kevintam
 * @since 2022-05-01
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
