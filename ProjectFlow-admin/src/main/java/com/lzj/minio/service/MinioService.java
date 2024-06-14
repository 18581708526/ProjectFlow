package com.lzj.minio.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    String upload(MultipartFile file);
    String upload(String name, InputStream inputStream, String contentType);
    void delete(String url);
    InputStream download(String url);
}
