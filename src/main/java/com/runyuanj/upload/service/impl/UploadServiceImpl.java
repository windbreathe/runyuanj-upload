package com.runyuanj.upload.service.impl;

import com.runyuanj.upload.service.UploadService;
import com.runyuanj.upload.utils.Constants;
import com.runyuanj.upload.utils.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @author: runyu
 * @date: 2019/12/24 17:59
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public List<String> uploadFile(MultipartFile[] files) throws Exception {
        if (files == null && files.length == 0) {
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

    // 实现断点续传功能
    private boolean uploadStream2AbsolutePath(MultipartFile multipartFile,
                                              String fileName,
                                              String tmpCatalog,
                                              String fileCatalog) throws Exception {
        File tmpPath = new File(tmpCatalog);
        if (!tmpPath.exists()) {
            boolean mkdirs = tmpPath.mkdirs();
            if (!mkdirs) {
                throw new Exception(tmpCatalog + " mkdirs failed");
            }
        }

        File targetPath = new File(fileCatalog);
        if (!targetPath.exists()) {
            boolean mkdirs = targetPath.mkdirs();
            if (!mkdirs) {
                throw new Exception(fileCatalog + " mkdirs failed");
            }
        }

        File tmpFile = new File(tmpPath, fileName);
        File targetFile = new File(targetPath, fileName);
        boolean result = uploadStream2TmpPath(multipartFile, tmpFile);

        if (result) {
            FileCopyUtils.copy(tmpFile, targetFile);
            tmpFile.delete();
            return true;
        }
        return false;
    }

    private boolean uploadStream2TmpPath(MultipartFile multipartFile, File tmpFile) throws IOException {
        boolean result = false;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = multipartFile.getInputStream();
            out = new FileOutputStream(tmpFile);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            result = true;
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    ;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
        return result;
    }

}
