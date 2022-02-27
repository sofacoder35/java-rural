package com.sofa.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class QiniuUtils {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Autowired
    private UploadManager uploadManager;

    public String uploadToken(){
        Auth auth=Auth.create(accessKey,secretKey);
        return auth.uploadToken(bucket);
    }

    /**
     * 根据文件路径上传
     * @param filepath
     * @param filename
     * @return
     */
    public String upload(String filepath,String filename){
        String name = this.getName(filename);
        try {
            Response response = uploadManager.put(filepath, name, this.uploadToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==>key:{} ==>hash:{}",putRet.key,putRet.hash);
            return name;
        } catch (QiniuException e) {
            Response r=e.response;
            try {
                log.error("文件上传失败==>{}",r.bodyString());
            }catch (QiniuException ex2){
                //
            }
           return null;
        }

    }

    /**
     * 根据字节
     * @param bytes
     * @param filename
     * @return
     */
    public String upload(byte[] bytes,String filename){
        String name = this.getName(filename);
        try {
            Response response = uploadManager.put(bytes, name, this.uploadToken());
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==>key:{} ==>hash:{}",putRet.key,putRet.hash);
            return name;
        } catch (QiniuException e) {
            Response r=e.response;
            try {
                log.error("文件上传失败==>{}",r.bodyString());
            }catch (QiniuException ex2){
                //
            }
           return null;
        }

    }

    /**
     * 根据流
     * @param stream
     * @param filename
     * @return
     */
    public String upload(InputStream stream, String filename){
        String name = this.getName(filename);
        try {
            Response response = uploadManager.put(stream, name, this.uploadToken(),null,null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功==>key:{} ==>hash:{}",putRet.key,putRet.hash);
            return name;
        } catch (QiniuException e) {
            Response r=e.response;
            try {
                log.error("文件上传失败==>{}",r.bodyString());
            }catch (QiniuException ex2){
                //
            }
           return null;
        }

    }

    public void delete(String fileName){
        Configuration cfg=new Configuration(Region.region2());
        Auth auth=Auth.create(accessKey,secretKey);
        BucketManager bucketManager=new BucketManager(auth,cfg);
        try{
            bucketManager.delete(bucket,fileName);
            log.info("删除成功");
        }catch (QiniuException ex){
            log.error("删除失败==>{}",ex.code());
            log.error(ex.response.toString());
        }
    }

    /**
     * 文件名前加时间避免重复
     * @param filename
     * @return
     */
    public String getName(String filename){
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) +filename;
    }

}
