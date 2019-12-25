package com.runyuanj.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: runyu
 * @date: 2019/12/24 17:57
 */
public interface UploadService {

   List<String> uploadFile(MultipartFile[] files) throws Exception;

}
