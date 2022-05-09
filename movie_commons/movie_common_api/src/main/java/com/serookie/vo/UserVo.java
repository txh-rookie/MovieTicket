package com.serookie.vo;

import lombok.Data;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/21
 */
@Data
public class UserVo {
    private String userId;
    private String nickname;
    private Integer userSex;
    private Boolean userStatus;
}
