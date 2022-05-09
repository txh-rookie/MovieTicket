package com.serookie.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.*;
import com.serookie.mapper.RoleMapper;
import com.serookie.mapper.RolePermissionMapper;
import com.serookie.mapper.UserMapper;
import com.serookie.mapper.UserRoleMapper;
import com.serookie.movie.utils.JwtTokenUtils;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.service.PermissionService;
import com.serookie.service.RoleService;
import com.serookie.service.UserRoleService;
import com.serookie.service.UserService;
import com.serookie.vo.RegisterVo;
import com.serookie.vo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/7
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;


    @Override
    public String login(UserLogin userLogin) {
        //判断一下账号密码是否为空
        String username = userLogin.getUsername();
        String password = userLogin.getPassword();
        //校验是否为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new CustomException(20001,"账号或密码为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        //根据账号查询用户
        User user = userMapper.selectOne(queryWrapper);
        if(StringUtils.isEmpty(user)){
            throw new CustomException(20001,"登录失败");
        }
        //对密码进行加密
        String mdPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!mdPassword.equals(user.getPassword())){
             throw new CustomException(20001,"账号或密码错误");
        }
        if(user.getUserStatus()==false){
            throw new CustomException(20001,"账号已被禁用");
        }
        String userJson = JSON.toJSONString(user);
        String jwtToken = jwtTokenUtils.createToken(user.getNickname(), user.getUserId());
        if(null==redisTemplate.opsForValue().get(jwtToken)){
            redisTemplate.opsForValue().set(jwtToken,userJson,1,TimeUnit.DAYS);//登录有效期为1天
        }
        return jwtToken;
    }
    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();//验证码
        String username = registerVo.getUsername();//用户名
        String password = registerVo.getPassword();//密码
        String email = registerVo.getEmail();//邮箱
        //非空校验
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(email)){
            throw new CustomException(20001,"注册失败");
        }
        //校验redis里面的验证码
        String redisCode = redisTemplate.opsForValue().get(email);
        if(StringUtils.isEmpty(redisCode)){
            throw new CustomException(20001,"注册失败");
        }
        if(!redisCode.equals(code)){
            throw new CustomException(20001,"注册失败");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        Long count = userMapper.selectCount(wrapper);
        if(count>0){
            throw new CustomException(20001,"注册失败");
        }
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        user.setNickname(username);
        user.setUserStatus(true);
        user.setRoleName("普通用户");
        user.setHeadUrl("https://apic.douyucdn.cn/upload/avatar_v3/201807/125191d284e40805300533323fce2f18_middle.jpg");
        userMapper.insert(user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user1 = userMapper.selectOne(queryWrapper);
        UserRole userRole = new UserRole();
        userRole.setUid(user1.getUserId());
        QueryWrapper<Role> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("role_name","普通用户");
        Role role = roleService.getOne(queryWrapper1);
        userRole.setRid(role.getId());
        userRoleService.save(userRole);
    }

    @Override
    public User selectById(String id) {
        User user = userMapper.selectById(id);
        if(StringUtils.isEmpty(user)){
            throw new CustomException(20001,"查询错误");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public Page<User> PageUser(Integer current, Integer limit) {
        Page<User> page = new Page<>(current, limit);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();
        records.forEach(elem->{
            QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uid",elem.getUserId());
            List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper);
            userRoles.forEach(v->{
                Role role = roleService.getById(v.getRid());
                elem.setRoleName(role.getRoleName());
            });
            elem.setPassword(null);
        });
        return userPage;
    }

    /**
     * 用户的登录
//     * @param user
     */
//    @Override
//    public String adminLogin(UserLogin user) {
//        if(StringUtils.isEmpty(user.getUsername())){
//            throw new CustomException(ResultEnum.NOT_PARAM_NULL);
//        }
//        if(StringUtils.isEmpty(user.getPassword())){
//            throw new CustomException(ResultEnum.NOT_PARAM_NULL);
//        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username",user.getUsername());
//        User user1 = userMapper.selectOne(queryWrapper);
//        if(StringUtils.isEmpty(user1)){
//            throw new CustomException(ResultEnum.LOGIN_ERROR);
//        }
//        String mdPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
//        if(!mdPassword.equals(user.getPassword())){
//            throw new CustomException(ResultEnum.LOGIN_ERROR);
//        }
//
//        String jwtToken = jwtTokenUtils.createToken(user1.getRoleName(), user1.getUserId());
//        return jwtToken;
//    }

    @Override
    public Map<String,Object> getAdminInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = userService.getOne(queryWrapper);
        if (null == user) {
           throw new CustomException(ResultEnum.LOGIN_ERROR);
        }
        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getUserId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }
        //根据用户id获取操作权限值
        result.put("name", user.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
//        result.put("permissionValueList", permissionValueList);
        return result;
    }
    @Override
    public User getUserInfo(String token) {
        Object obj = redisTemplate.opsForValue().get(token);
        User user = JSON.parseObject((String) obj, User.class);
        user.setPassword(null);
        QueryWrapper<UserRole> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("uid",user.getUserId());
        UserRole userRole = userRoleService.getOne(queryWrapper2);
        Role role = roleService.getById(userRole.getRid());
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",role.getId()).select("permission_id");
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper);
        List<Permission> permissionList=new ArrayList<>();
        rolePermissions.forEach(elem->{
            Permission permissionServiceById = permissionService.getById(elem.getPermissionId());
            permissionList.add(permissionServiceById);
        });
        List<Permission> permissionList1 = PermissionServiceImpl.buildPermission(permissionList);
        user.setPermissionList(permissionList1);
        return user;
    }

    @Override
    public List<Permission> getMenu(String roleName) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name",roleName);
        Role role = roleService.getOne(queryWrapper);
//        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("rid",role.getId());
//        UserRole userRole = userRoleService.getOne(queryWrapper1);
//        userRole.getRid()
        QueryWrapper<RolePermission> queryWrapper1 = new QueryWrapper<>();
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper1);
        List<Permission> permissionList=new ArrayList<>();
        rolePermissions.forEach(elem->{
            Permission permission = permissionService.getById(elem.getPermissionId());
            permissionList.add(permission);
        });
        List<Permission> permissionList1 = PermissionServiceImpl.buildPermission(permissionList);
        return permissionList1;
    }
}
