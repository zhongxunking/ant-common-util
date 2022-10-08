/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-23 17:00 创建
 */
package org.antframework.common.util.tostring.mask;

/**
 * 掩码工具
 */
public final class MaskUtils {
    // 默认掩码字符
    private static final char DEFAULT_MASK_CHAR = '*';

    /**
     * 掩码
     *
     * @param str       需被掩码的字符串
     * @param startSize 前段明文长度
     * @param endSize   末段明文长度
     * @return 掩码后的字符串
     * @throws IllegalArgumentException startSize小于0或endSize小于0
     */
    public static String mask(CharSequence str, int startSize, int endSize) {
        return mask(str, startSize, endSize, DEFAULT_MASK_CHAR);
    }

    /**
     * 掩码
     *
     * @param str       需被掩码的字符串
     * @param startSize 前段明文长度
     * @param endSize   末段明文长度
     * @param maskChar  掩码字符
     * @return 掩码后的字符串
     * @throws IllegalArgumentException startSize小于0或endSize小于0
     */
    public static String mask(CharSequence str, int startSize, int endSize, char maskChar) {
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
        for (int i = 0; i < startSize; i++) {
            builder.append(str.charAt(i));
        }
        // 构造中段掩码
        int maskEndIndex = str.length() - endSize;
        maskEndIndex = maskEndIndex < startSize ? startSize : maskEndIndex;
        for (int i = startSize; i < maskEndIndex; i++) {
            builder.append(maskChar);
        }
        // 构造末段明文
        for (int i = maskEndIndex; i < str.length(); i++) {
            builder.append(str.charAt(i));
        }

        return builder.toString();
    }
}
