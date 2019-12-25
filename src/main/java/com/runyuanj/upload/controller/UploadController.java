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
 * @author: runyu
 * @date: 2019/12/24 17:43
 */
@CrossOrigin("*")
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

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
