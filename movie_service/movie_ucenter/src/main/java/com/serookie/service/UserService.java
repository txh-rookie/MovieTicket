package com.serookie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Cinema;
import com.serookie.entity.Movie;
import com.serookie.entity.Permission;
import com.serookie.entity.User;
import com.serookie.vo.RegisterVo;
import com.serookie.vo.UserLogin;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    String login(UserLogin userLogin);
    void register(RegisterVo registerVo);
    User selectById(String id);
    Page<User> PageUser(Integer current, Integer limit);
    Map<String,Object> getAdminInfo(String username);

    User getUserInfo(String token);

    List<Permission> getMenu(String roleName);
}
