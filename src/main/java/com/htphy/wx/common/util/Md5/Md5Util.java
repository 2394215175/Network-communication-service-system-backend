package com.htphy.wx.common.util.Md5;

import java.security.MessageDigest;

/**
 * @author dengwenning
 * @version 1.0
 * @description TODO md5加密工具类
 * @date 2020/10/10 10:02
 */

public class Md5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5;
        try {
            //引用  java.security.MessageDigest公共类
            // getInstance("MD5")方法 设置加密格式
            md5 = MessageDigest.getInstance("MD5");  //此句是核心
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
}
