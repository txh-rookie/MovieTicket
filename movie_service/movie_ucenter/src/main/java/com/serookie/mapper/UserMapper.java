package com.serookie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.serookie.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {
}
