package com.runyuanj.upload.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author: runyu
 * @date: 2019/12/25 8:40
 */
public class FileUploadUtil {

    private FileUploadUtil() {}

    /**
     * 从字典中匹配数据类型
     *
     * @param fileType
     * @return
     */
    public static String getFileType(String fileType) {
        if (StringUtils.isBlank(fileType)) {
            return "";
        }
        if (!fileType.startsWith(".")) {
            fileType = "." + fileType;
        }
        for (String type : Constants.IMAGE_EXT) {
            if (type.equalsIgnoreCase(fileType)) {
                return "image";
            }
        }
        for (String type : Constants.VIDEO_EXT) {
            if (type.equalsIgnoreCase(fileType)) {
                return "video";
            }
        }
        for (String type : Constants.AUDIO_EXT) {
            if (type.equalsIgnoreCase(fileType)) {
                return "audio";
            }
        }
        return "";
    }

    /**
     * 获取中间路径, yyyy/MM/dd/
     *
     * @return
     */
    public static String getCalendarMidPath() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new StringBuffer()
                .append(year).append(File.separator)
                .append(month).append(File.separator)
                .append(day).append(File.separator)
                .toString();
    }

    /**
     * 获取文件后缀
     *
     * @param originalFilename
     * @return fileSuffix
     * @throws Exception
     */
    public static String getFileSuffix(String originalFilename) throws Exception {
        String suffix = StringUtils.substringAfterLast(originalFilename, ".");
        String fileType = FileUploadUtil.getFileType(suffix);
        if (StringUtils.isBlank(fileType)) {
            throw new Exception("fileType " + suffix + " not support");
        }
        return suffix;
    }

    /**
     * 创建catalog目录, catalog不能包含文件名, 且必须以'/'结尾. 返回file对象
     *
     * @param catalog
     * @return file
     * @throws Exception
     */
    public static File createAndCheckFilePath(String catalog) throws Exception {
        if (StringUtils.isBlank(catalog)) {
            throw new Exception("catalog can not be null");
        }
        File filePath = new File(catalog);
        if (!filePath.exists()) {
            boolean mkdirs = filePath.mkdirs();
            if (!mkdirs) {
                throw new Exception(catalog + " mkdirs failed");
            }
        }
        return filePath;
    }

    /**
     * 通过url获取文件md5, 先下载下来再计算, 毫无作用
     *
     * @param
     * @return
     *//*
    public static String getFileMd5(String fileUrl, Integer connTimeOut, Integer readTimeOut) throws IOException {

        if (org.springframework.util.StringUtils.isEmpty(fileUrl)) {
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
    }*/
    public static void main(String[] args) {
        // 验证fileType
        /* String type = "jpg";
        String type1 = type + "";
        String jpg = getFileType(type1);
        System.out.println(jpg);
        System.out.println(type1);*/
        // 验证时间
        /*String calendarMidPath = getCalendarMidPath();
        System.out.println(calendarMidPath);*/
        // 验证创建目录
        /*try {
            File andCheckFilePath = createAndCheckFilePath("./data/123.mp4");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

}
