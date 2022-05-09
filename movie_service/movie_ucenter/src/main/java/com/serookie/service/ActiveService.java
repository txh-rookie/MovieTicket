package com.serookie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.serookie.entity.Active;
import com.serookie.entity.User;

public interface ActiveService extends IService<Active> {
    Boolean signUp(String uid,String activeId);
}
