package com.runyuanj.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: runyu
 * @date: 2019/12/24 17:57
 */
public interface UploadService {

   List<String> uploadFile(MultipartFile[] files) throws Exception;

    /**
     * 查询服务器中是否有该文件, 如果有且已上传完成, 返回地址, 若未上传完成, 返回上传到第几片. 没有返回空
     *
     * @param md5
     * @return
     */
    String checkFileMd5(String md5);
}
