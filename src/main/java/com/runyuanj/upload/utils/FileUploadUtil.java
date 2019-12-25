package com.runyuanj.upload.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author: runyu
 * @date: 2019/12/25 8:40
 */
public class FileUploadUtil {

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
     * @return
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

    public static void main(String[] args) {
        // 验证fileType
        /* String type = "jpg";
        String type1 = type + "";
        String jpg = getFileType(type1);
        System.out.println(jpg);
        System.out.println(type1);*/
        // 验证时间
        String calendarMidPath = getCalendarMidPath();
        System.out.println(calendarMidPath);
    }


}
