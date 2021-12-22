package com.serookie.movie.controller;

import com.serookie.entity.Result;
import com.serookie.movie.service.MultipartService;
import com.serookie.movie.utils.CosUtils;
import com.serookie.movie.utils.handler.CustomException;
import com.serookie.movie.utils.handler.GlobalExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@Api(value="文件上传的接口",tags={"文件上传的接口"})
@RequestMapping("/cos")
@Slf4j
public class MultipartFileController {

    @Value("${tencent.path}")
    private String COS_PATH;

    @Resource
    private MultipartService service;

    @PostMapping("/add")
    @ApiOperation("上传文件")
    public Result multipartAdd(@ApiParam("文件上传的参数") @RequestParam("file")MultipartFile multipartFile){
        String url = service.addMultipart(multipartFile);
        return Result.ok().message("文件上传成功").data("url",COS_PATH+url);
    }



}
