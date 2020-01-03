package com.runyuanj.upload.service;

/**
 * @author: runyu
 * @date: 2020/1/3 13:43
 */
public interface AsyncService {

    void download(String sourceUrl, String target, Integer readTimeout, Integer connTimeout, boolean isOverwrite) throws Exception;
}
