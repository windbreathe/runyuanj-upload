package com.runyuanj.upload.service.impl;

import com.runyuanj.upload.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author: runyu
 * @date: 2020/1/3 13:43
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    /**
     * RandomAccessFile 实现断点续传
     *
     * @param sourceUrl
     * @param target
     * @param readTimeout 读取超时时间
     * @param connTimeout 连接超时时间,
     * @param isOverwrite
     * @return
     * @throws Exception
     */
    @Async
    public void download(String sourceUrl, String target, Integer readTimeout, Integer connTimeout, boolean isOverwrite) throws Exception {

        long start = 0L;

        File targetFile = new File(target);
        File tmpFile = new File(targetFile + ".tmp");
        if (targetFile.exists()) {
            if (isOverwrite) {
                FileUtils.deleteQuietly(tmpFile);
                FileUtils.deleteQuietly(targetFile);
            } else {
                return;
            }
        }

        File file = new File(targetFile.getParent());

        if (file.exists()) {
            start = tmpFile.length();
        } else {
            file.mkdirs();
        }

        URL url = new URL(sourceUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(readTimeout == null ? 1000 : readTimeout);
        conn.setConnectTimeout(connTimeout == null ? 1000 : connTimeout);
        conn.setRequestProperty("RANGE", "bytes=" + start + "-");
        conn.connect();

        InputStream in = null;
        try {
            in = conn.getInputStream();
        } catch (IOException e) {
            conn.disconnect();
            return;
        }

        RandomAccessFile randomAccessFile = new RandomAccessFile(tmpFile, "rw");
        randomAccessFile.seek(start);

        long length = start;
        byte[] buff = new byte[100 * 1024];
        int len;
        while ((len = in.read(buff)) != -1) {
            randomAccessFile.write(buff, 0, len);
            length += len;
        }
        IOUtils.closeQuietly(in);
        if (conn != null) {
            conn.disconnect();
        }
        FileUtils.copyFile(tmpFile, targetFile);
        FileUtils.deleteQuietly(tmpFile);
        log.info("{} 下载结束", sourceUrl);
        return;
    }
}
