package com.runyuanj.upload.controller;

import com.runyuanj.upload.response.ServiceResponse;
import com.runyuanj.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 1. 普通上传
 * 2. 秒传
 * 3. 断点续传
 * 4. 分片上传
 * 5. 文件夹上传
 *
 * 进阶版:
 *  NIO上传
 *  多线程处理
 *
 * @author: runyu
 * @date: 2019/12/24 17:43
 */
@CrossOrigin("*")
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 检查文件MD5, 实现秒传
     *
     * @param md5
     * @return
     */
    @PostMapping("/check/file")
    public ServiceResponse checkFile(String md5) {
        String url = uploadService.checkFileMd5(md5);
        return new ServiceResponse(HttpStatus.OK, null, url);
    }

    /**
     * 批量上传文件
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/file")
    public ServiceResponse uploadFile(MultipartFile[] file) {
        try {
            List<String> strings = uploadService.uploadFile(file);
            return new ServiceResponse(HttpStatus.OK, null, strings);
        } catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
