package com.runyuanj.upload.utils;

/**
 * @author: runyu
 * @date: 2019/12/30 16:58
 */
public class CodeUtil {

    /**
     * byte[]转HexString
     *
     * @param bytes
     * @return
     */
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

}
