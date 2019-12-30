package com.runyuanj.upload.service.impl;

import com.runyuanj.upload.service.UploadService;
import com.runyuanj.upload.utils.Constants;
import com.runyuanj.upload.utils.FileUploadUtil;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: runyu
 * @date: 2019/12/24 17:59
 */
@Service
public class UploadServiceImpl implements UploadService {

    /**
     * 查询服务器中是否有该文件, 如果有且已上传完成, 返回地址, 若未上传完成, 返回上传到第几片. 没有返回空
     *
     * @param md5
     * @return
     */
    @Override
    public String checkFileMd5(String md5) {
        // 查询文件md5记录
        return null;
    }

    /**
     * 通过url获取文件md5, 先下载下来再计算
     *
     * @param fileUrl
     * @return
     */
    public String getFileMd5(String fileUrl, Integer connTimeOut, Integer readTimeOut) throws IOException {

        if (StringUtils.isEmpty(fileUrl)) {
            return null;
        }

        URL url = new URL(fileUrl);

        HttpURLConnection conn = null;
        InputStream in = null;
        try {

            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(connTimeOut);
            conn.setReadTimeout(readTimeOut);
            conn.connect();

            in = conn.getInputStream();

            return DigestUtils.md5DigestAsHex(in);
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {

                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {

                }
            }
        }
    }


    @Override
    public List<String> uploadFile(MultipartFile[] files) throws Exception {
        if (files == null && files.length <= 0) {
            throw new Exception("File is Null");
        }

        List<String> results = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            String path = uploadFile2Server(multipartFile);
            results.add(path);
        }

        return results;
    }

    private String uploadFile2Server(MultipartFile multipartFile) throws Exception {
        // 根据原文件名生成新文件名和地址
        String originalFilename = multipartFile.getOriginalFilename();

        String suffix = FileUploadUtil.getFileSuffix(originalFilename); // 不包括".";
        String fileType = FileUploadUtil.getFileType(suffix); // image, video, audio

        String fileName = new StringBuffer(UUID.randomUUID().toString().replaceAll("-", ""))
                .append(".").append(suffix).toString();

        String midPath = FileUploadUtil.getCalendarMidPath();
        String fileCatalog = new StringBuffer(Constants.serverRootPath).append(fileType).append(File.separator).append(midPath).toString();
        String tmpCatalog = new StringBuffer(Constants.serverTempPath).append(fileType).append(File.separator).append(midPath).toString();

        String url = new StringBuffer(Constants.domain).append(fileType).append(File.separator).append(midPath).append(fileName).toString();

        // 实现分片上传
        boolean result = uploadStream2AbsolutePath(multipartFile, fileName, tmpCatalog, fileCatalog);
        if (result) {
            return url;
        } else {
            throw new Exception("Upload File Failed!");
        }
    }

    private boolean uploadStream2AbsolutePath(MultipartFile multipartFile,
                                              String fileName,
                                              String tmpCatalog,
                                              String fileCatalog) throws Exception {

        File tmpPath = FileUploadUtil.createAndCheckFilePath(tmpCatalog);
        File targetPath = FileUploadUtil.createAndCheckFilePath(fileCatalog);

        File tmpFile = new File(tmpPath, fileName);
        File targetFile = new File(targetPath, fileName);

        boolean result = FileUploadUtil.uploadStream2TmpPath(multipartFile, tmpFile);

        if (result) {
            FileCopyUtils.copy(tmpFile, targetFile);
            tmpFile.delete();
            return true;
        }
        return false;
    }

    boolean uploadFileToServer(MultipartFile multipartFile, File file) throws Exception {

        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);


        return false;

    }


}
