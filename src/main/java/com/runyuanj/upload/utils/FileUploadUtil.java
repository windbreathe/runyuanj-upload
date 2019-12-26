package com.runyuanj.upload.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author: runyu
 * @date: 2019/12/25 8:40
 */
public class FileUploadUtil {

    public static boolean uploadStream2TmpPath(MultipartFile multipartFile, File tmpFile) throws IOException {
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
        if (!StringUtils.endsWith(catalog, "/")) {
            throw new Exception("catalog must endsWith '/'");
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

    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str;
        for (int i = 0; i < bytes.length; i++) {

            str = Integer.toHexString(bytes[i] & 0xFF).toUpperCase();
            if (str.length() < 2) {
                sb.append(0);
            }

            sb.append(str);
        }

        return sb.toString();
    }


    /**
     * convert HexString to byte[]
     *
     * @param str
     * @return
     */
    public static byte[] hexStringToBytes(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];

        for (int i = 0; i < byteArray.length; i++) {

            String subStr = str.substring(2 * i, 2 * i + 2);

            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

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
