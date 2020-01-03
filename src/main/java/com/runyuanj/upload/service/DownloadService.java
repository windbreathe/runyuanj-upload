package com.runyuanj.upload.service;

/**
 * @author: runyu
 * @date: 2020/1/3 10:48
 */
public interface DownloadService {

    String downloadByUrl(String url, String fileType, Integer readTimeout, Integer connTimeout) throws Exception;

}
