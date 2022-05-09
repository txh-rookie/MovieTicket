package com.serookie.service;

public interface MsmService {
    //发送验证码
    public boolean send(String email);

    boolean sendOrder(String email, String message,String orderId);
}
