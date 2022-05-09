package com.serookie.movie.service;

import org.springframework.web.multipart.MultipartFile;

public interface MultipartService {
    //文件上传
    String addMultipart(MultipartFile multipartFile);
    //文件下载
    boolean delMultipart(String key);
}
