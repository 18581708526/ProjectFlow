package com.lzj.minio.controller;

import com.lzj.minio.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/minio/file")
public class FileController {
    @Autowired
    private MinioService minIOService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) {
        String url = minIOService.upload(file);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping
    public ResponseEntity delete(String url) {
        minIOService.delete(url);
        return ResponseEntity.ok("删除成功");
    }
}
