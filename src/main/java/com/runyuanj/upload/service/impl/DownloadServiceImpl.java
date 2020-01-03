package com.runyuanj.upload.service.impl;

import com.runyuanj.upload.service.AsyncService;
import com.runyuanj.upload.service.DownloadService;
import com.runyuanj.upload.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @author: runyu
 * @date: 2020/1/3 10:49
 */
@Slf4j
@Service
public class DownloadServiceImpl implements DownloadService {

    private static String rootServerPath = "/usr/share/nginx/html/runyu/";
    private static String domain = "http://www.runyuanj.com/runyu/";

    @Autowired
    private AsyncService asyncService;

    @Override
    public String downloadByUrl(String url, String fileType, Integer readTimeout, Integer connTimeout) throws Exception {

        String midPath = FileUploadUtil.getCalendarMidPath();
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String type = FileUploadUtil.getFileType(fileType);
        String filePath =rootServerPath + type + File.separator + midPath + fileName + "." + fileType;
        asyncService.download(url, filePath, readTimeout, connTimeout, true);
        log.info("{} 开始下载", url);
        return domain  + type + File.separator + midPath + fileName + "." + fileType;
    }
}
