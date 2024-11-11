package com.lzj.minio.service.impl;

import com.lzj.filedetail.domain.FileDetail;
import com.lzj.filedetail.service.IFileDetailService;
import com.lzj.minio.domain.MinioProperties;
import com.lzj.minio.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Service
public class MinioServiceImpl implements MinioService {
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties properties;
    @Autowired
    private IFileDetailService fileDetailService;


    /**
     * 上传文件
     * @param  file file
     * 上传文件到对象存储服务器minio上 :地址：http://192.168.154.128:9000
     * @return String
     */
    @Override
    public String upload(MultipartFile file) {
        // 原文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 构造新的文件名，名字不重复
        String objectName = UUID.randomUUID().toString() + suffix;
        String bucketName = properties.getBucketName();
        String FileUrl=properties.getDomain() + "/" + bucketName + "/" + objectName;
        // 上传文件
        try{
            @Cleanup
            InputStream stream = file.getInputStream();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType(file.getContentType())
                    .stream(stream, file.getSize(), -1)
                    .bucket(bucketName)
                    .object(objectName)
                    .build();
            minioClient.putObject(putObjectArgs);
            //保存文件明细到数据库
            FileDetail fileDetail= new FileDetail();
            fileDetail.setFileName(originalFilename);
            fileDetail.setFileUrl(FileUrl);
            fileDetailService.insertFileDetail(fileDetail);

        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }

        return FileUrl;
    }
    /**
     * 上传文件
     * @param name name
     * @param inputStream inputStream
     * @param contentType contentType
     * @return String
     */
    @Override
    public String upload(String name, InputStream inputStream, String contentType) {
        // 上传文件
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .contentType(contentType)
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(properties.getBucketName())
                    .object(name)
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败: " + e.getMessage());
        }
        return properties.getEndpoint() + "/" + properties.getBucketName() + "/" + name;
    }
    /**
     * 删除文件
     * @param url url
     */
    @Override
    public void delete(String url) {
        String objectName = url.replace(properties.getEndpoint()+"/","")
                .replace(properties.getBucketName()+"/","");
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build();
        try {
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败: " + e.getMessage());
        }
    }
    /**
     * 下载文件
     * @param url url
     * @return InputStream
     */
    @Override
    public InputStream download(String url) {
        String objectName = url.replace(properties.getEndpoint()+"/","")
                .replace(properties.getBucketName()+"/","");
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(properties.getBucketName())
                .object(objectName)
                .build();
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(args);
        } catch (Exception e) {
            throw new RuntimeException("下载文件失败: " + e.getMessage());
        }
        return inputStream;

    }
}
