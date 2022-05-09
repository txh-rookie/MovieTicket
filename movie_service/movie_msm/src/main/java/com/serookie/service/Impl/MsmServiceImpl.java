package com.serookie.service.Impl;

import com.serookie.movie.utils.handler.CustomException;
import com.serookie.service.MsmService;
import com.serookie.utils.RandomUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author kevintam
 * @version 1.0
 * @title
 * @description
 * @createDate 2022/4/4
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 发送验证码
     */
    @Override
    public boolean send(String email) {
        //校验邮箱是否为null
        if(email.isEmpty() && email==null){
            return false;
        }
        //校验邮箱的格式
        if(!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email)){
             return false;
        }
        String code = redisTemplate.opsForValue().get(email);
        if(StringUtils.isEmpty(code)){
            code=RandomUtils.getNumberCode().toString();
        }
        //查看剩余的时间
//        code = RandomUtils.getNumberCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码测试");
        message.setText("您正在申请的验证码为:"+code+","+"有效期为5分钟");
        message.setFrom("843808107@qq.com");
        message.setTo(email);
        //设置了一个为期5分钟的字符 k为邮箱 v为验证码.
        redisTemplate.opsForValue().set(email,code.toString(), 5,TimeUnit.MINUTES);
        try{
            mailSender.send(message);
        }catch(CustomException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean sendOrder(String email, String message,String orderId) {
        //校验邮箱是否为null
        if(email.isEmpty() && email==null){
            return false;
        }
        //校验邮箱的格式
        if(!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email)){
            return false;
        }
        //查看剩余的时间
//        code = RandomUtils.getNumberCode();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("验证码测试");
        mailMessage.setText("您在本网站已购买电影票,"+"订单号为:"+orderId+",请在"+message+"去影院进行观看");
        mailMessage.setFrom("843808107@qq.com");
        mailMessage.setTo(email);
        try{
            mailSender.send(mailMessage);
        }catch(CustomException e){
            e.printStackTrace();
        }
        return true;
    }
}
