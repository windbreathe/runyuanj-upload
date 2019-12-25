package com.runyuanj.upload.utils;

import java.util.*;

/**
 * Copyright：botBrain.ai
 * Author: SongXiaoGuang
 * Date: 2018/6/9.
 * Description:
 */
public class Constants {
    public static final Set<String> FILE_TYPES;
    public static final String FILE_IMAGE = "image";
    public static final String FILE_AUDIO = "audio";
    public static final String FILE_VIDEO = "video";
    public static final String FILE_SYSTEM = "system";


    static {
        Set<String> set = new HashSet<>();
        set.add(FILE_IMAGE);
        set.add(FILE_AUDIO);
        set.add(FILE_VIDEO);
        set.add(FILE_SYSTEM);
        FILE_TYPES = Collections.unmodifiableSet(set);
    }

    public static final List<String> FILE_EXT;
    public static final List<String> VIDEO_EXT;
    public static final List<String> IMAGE_EXT;
    public static final List<String> AUDIO_EXT;

    static {
        String[] strs = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png", ".mp4", ".mpeg", ".mpg", ".avi", ".flv", ".wmv", ".mov", ".mp3", ".wav", ".webm"};
        FILE_EXT = Collections.unmodifiableList(Arrays.asList(strs));
        String[] imageExts = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};
        IMAGE_EXT = Collections.unmodifiableList(Arrays.asList(imageExts));
        String[] videoExts = new String[]{".mp4", ".mpeg", ".mpg", ".avi", ".flv", ".wmv", ".mov"};
        VIDEO_EXT = Collections.unmodifiableList(Arrays.asList(videoExts));
        String[] audioExts = new String[]{".mp3", ".wav", ".webm"};
        AUDIO_EXT = Collections.unmodifiableList(Arrays.asList(audioExts));
    }
    public static final String serverTempPath = "/tmp/upload/";
    public static final String serverRootPath = "/usr/share/nginx/html/runyu/";
    public static final String domain = "http://www.runyuanj.com/runyu/";
}
