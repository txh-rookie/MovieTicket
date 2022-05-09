package com.serookie.utils;

import java.util.Random;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/5
 */
public class RandomUtils {
    //验证码的长度
    public static final Integer CODE_LENGTH=6;
    //验证码的范围
    public static final String NUMBER_CHAR = "0123456789";

    public static StringBuilder getNumberCode(){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = CODE_LENGTH; i > 1; i--) {
            int index = random.nextInt(NUMBER_CHAR.length());
//            System.out.println("index:"+index);
            char str = NUMBER_CHAR.charAt(index);
            builder.append(str);
        }
        return builder;
    }
}
