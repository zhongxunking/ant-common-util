/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2021-03-25 13:07 创建
 */
package org.antframework.common.util.digest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要工具
 */
public class Digests {
    // md5
    private static final String MD5_ALGORITHM_NAME = "MD5";
    // 十六进制字符
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成十六进制md5摘要
     *
     * @param str 生成摘要的字符串
     * @return 十六进制md5摘要
     */
    public static String md5DigestAsHex(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return md5DigestAsHex(bytes);
    }

    /**
     * 生成十六进制md5摘要
     *
     * @param bytes 生成摘要的字节数组
     * @return 十六进制md5摘要
     */
    public static String md5DigestAsHex(byte[] bytes) {
        byte[] digest = getDigest(MD5_ALGORITHM_NAME).digest(bytes);
        char[] hexDigest = encodeHex(digest);
        return new String(hexDigest);
    }

    // 获取MessageDigest
    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    // 转换成十六进制
    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return chars;
    }
}
