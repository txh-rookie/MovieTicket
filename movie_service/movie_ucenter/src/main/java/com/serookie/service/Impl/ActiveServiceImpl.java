package com.serookie.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.serookie.entity.Active;
import com.serookie.mapper.ActiveMapper;
import com.serookie.mapper.UserMapper;
import com.serookie.service.ActiveService;
import com.serookie.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/22
 */
@Service
public class ActiveServiceImpl extends ServiceImpl<ActiveMapper, Active> implements ActiveService {

    @Resource
    private ActiveMapper activeMapper;

    @Override
    public Boolean signUp(String uid,String activeId) {
        Active active = activeMapper.selectById(activeId);
        if(StringUtils.isEmpty(active.getUid())){
            active.setUid(uid);
        }else{
            StringBuffer buffer = new StringBuffer(active.getUid());
            StringBuffer append = buffer.append("#" + uid);
            String str = append.toString();
            active.setUid(str);
        }
        return activeMapper.updateById(active)>0;
    }
}
