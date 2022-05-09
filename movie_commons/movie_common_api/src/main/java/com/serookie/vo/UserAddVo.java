package com.serookie.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/5/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddVo {
    private String username;
    private String password;
    private String roleId;
}
