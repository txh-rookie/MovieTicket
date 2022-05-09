package com.serookie.movie.service.Impl;

import com.qcloud.cos.exception.CosClientException;
import com.serookie.movie.service.MultipartService;
import com.serookie.movie.utils.CosUtils;
import com.serookie.movie.utils.handler.CustomException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MultipartServiceImpl implements MultipartService {

    @Override
    public String addMultipart(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()){
            throw new CustomException(4000,"文件为空");
        }
        //文件名
        String name = multipartFile.getOriginalFilename();
        //判断一下是否有后缀
        if(name.lastIndexOf(".") <0){
            throw new CustomException(4001,"文件格式异常");
        }
        //判断文件的后缀
        String prefix = name.substring(name.lastIndexOf("."));
        if(!prefix.equalsIgnoreCase(".jpg")&&!prefix.equalsIgnoreCase(".jpeg")
                && !prefix.equalsIgnoreCase(".svg")&&!prefix.equalsIgnoreCase(".png")){
            throw new CustomException(4002,"上传文件格式异常");
        }
        //上传文件
        //使用uuid作为文件名，防止生成的临时文件重复
        final File excelFile;
        try {
            excelFile = File.createTempFile("imagesFile-" + System.currentTimeMillis(), prefix);
            multipartFile.transferTo(excelFile);
            String url = CosUtils.uploadfile(excelFile, "movie");
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(4003,e.getMessage());
        }
    }
    /**
     * 删除
     * @param key
     * @return
     */
    @Override
    public boolean delMultipart(String key) {
        if(Strings.isEmpty(key)){
            throw new CustomException(5000,"文件名不能空");
        }
        try {

            CosUtils.deletefile("/movie/"+key);
        } catch (CosClientException e) {
            throw new CustomException(5001,"文件删除错误");
        }
        return true;
    }
}
