/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-23 17:00 创建
 */
package org.antframework.common.util.tostring.format;

/**
 * 掩码工具
 */
public class MaskUtil {
    // 默认掩码字符
    private static final char DEFAULT_MASK_CHAR = '*';

    /**
     * 掩码
     *
     * @param str       需被淹吗的字符串
     * @param startSize 前段明文长度
     * @param endSize   后段明文长度
     * @return 掩码后的字符串
     * @throws IllegalArgumentException startSize小于0或endSize小于0
     */
    public static String mask(String str, int startSize, int endSize) {
        return mask(str, startSize, endSize, DEFAULT_MASK_CHAR);
    }

    /**
     * 掩码
     *
     * @param str       需被淹吗的字符串
     * @param startSize 前段明文长度
     * @param endSize   后段明文长度
     * @param maskChar  掩码字符
     * @return 掩码后的字符串
     * @throws IllegalArgumentException startSize小于0或endSize小于0
     */
    public static String mask(String str, int startSize, int endSize, char maskChar) {
        if (startSize < 0 || endSize < 0) {
            throw new IllegalArgumentException("startSize和endSize不能小于0");
        }
        if (str == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(str.length());
        // 构造前段明文
        if (startSize > str.length()) {
            startSize = str.length();
        }
        builder.append(str.substring(0, startSize));
        // 构造中段掩码
        int maskEndIndex = str.length() - endSize;
        maskEndIndex = maskEndIndex < startSize ? startSize : maskEndIndex;
        for (int i = startSize; i < maskEndIndex; i++) {
            builder.append(maskChar);
        }
        // 构造后段明文
        builder.append(str.substring(maskEndIndex));

        return builder.toString();
    }
}
