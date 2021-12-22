package com.serookie.movie.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.Random;

public class CosUtils {
    // 此处填写的存储桶名称
    private static final String bucketName = "blog-1300811828";
    //secretId
    private static final String SecretId = "AKIDrWHnOlxuXxZMwKvhkX6ZsUn10KQMhF7k";
    //SecretKey
    private static final String SecretKey = "YqRmtpgLmzzoNg9j6LPEBsLMk01tX6R9";


    // 1 初始化用户身份信息(secretId, secretKey，可在腾讯云后台中的API密钥管理中查看！
    private static COSCredentials cred = new BasicCOSCredentials(SecretId,SecretKey);
    // 2 设置bucket的区域, COS地域的简称请参照
    //    // https://cloud.tencent.com/document/product/436/6224，根据自己创建的存储桶选择地区
    private static ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));

    /**
     * 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20 M 以下的文件使用该接口 大文件上传请参照 API 文档高级 API 上传
     *
     * @param localfile 要上传的文件
     */
    public static String uploadfile(File localfile, String pathPrefix){
        COSClient cosClient = new COSClient(cred,clientConfig);
        String fileName = "";
        try {
            fileName =localfile.getName();
            String subString = fileName.substring(fileName.lastIndexOf("."));
            Random random = new Random();
            // 指定要上传到 COS 上的路径
            fileName = pathPrefix+"/"+random.nextInt(1000)+System.currentTimeMillis()+subString;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,fileName,localfile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
        return fileName;
    }

    public static void downFile(){
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred,clientConfig);
        //要下载的文件路径和名称
        String key = "/Users/kevintam/project/cosdown";
        // 指定文件的存储路径
        File downFile = new File("/Users/kevintam/project/cosdown");
        // 指定要下载的文件所在的 bucket 和对象键
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName,key);
        ObjectMetadata down = cosClient.getObject(getObjectRequest,downFile);

    }

    /**
     * 删除文件
     */
    public static void deletefile(String key) throws CosClientException, CosServiceException {
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // 指定要删除的 bucket 和路径
        cosclient.deleteObject(bucketName, key);
        // 关闭客户端(关闭后台线程)
        cosclient.shutdown();
    }
}
