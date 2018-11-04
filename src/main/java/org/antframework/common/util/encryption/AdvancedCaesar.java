/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-11-04 12:49 创建
 */
package org.antframework.common.util.encryption;

import java.util.*;

/**
 * 改进的凯撒加密（线程安全）
 * <p>
 * 加密规则：明文转换文密文时，会根据明文的值进行动态漂移
 */
public class AdvancedCaesar {
    // 原始字符序列
    private final char[] originalChars;
    // 原始的字符对应序号的map
    private final Map<Character, Integer> originalIndexMap;
    // 打乱后的字符序列
    private final char[] shuffledChars;
    // 打乱后的字符对应序号的map
    private final Map<Character, Integer> shuffledIndexMap;

    /**
     * 创建加密器
     *
     * @param chars 原始字符序列
     * @param seed  加密种子
     */
    public AdvancedCaesar(char[] chars, long seed) {
        originalChars = Arrays.copyOf(chars, chars.length);
        originalIndexMap = getIndexMap(originalChars);
        shuffledChars = shuffleNewChars(chars, seed);
        shuffledIndexMap = getIndexMap(shuffledChars);
    }

    // 获取字符对应序号的map
    private Map<Character, Integer> getIndexMap(char[] chars) {
        Map<Character, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            indexMap.put(chars[i], i);
        }
        return indexMap;
    }

    // 创建新字符数组，并打乱字符顺序
    private char[] shuffleNewChars(char[] chars, long seed) {
        List<Character> shufflingChars = new ArrayList<>(chars.length);
        for (char ch : chars) {
            shufflingChars.add(ch);
        }
        Collections.shuffle(shufflingChars, new Random(seed));

        char[] shuffledChars = new char[chars.length];
        for (int i = 0; i < shuffledChars.length; i++) {
            shuffledChars[i] = shufflingChars.get(i);
        }
        return shuffledChars;
    }

    /**
     * 加密
     *
     * @param str 明文
     * @return 密文
     */
    public String encode(String str) {
        StringBuilder builder = new StringBuilder(str);
        encodeInner(builder);
        builder.reverse();
        encodeInner(builder);
        return builder.toString();
    }

    // 加密
    private void encodeInner(StringBuilder builder) {
        int sum = 0;
        for (int i = 0; i < builder.length(); i++) {
            Integer index = originalIndexMap.get(builder.charAt(i));
            if (index == null) {
                throw new IllegalArgumentException(String.format("存在非法加密字符'%c'", builder.charAt(i)));
            }
            int temp = index;
            index += sum;
            index %= originalChars.length;
            builder.setCharAt(i, shuffledChars[index]);
            sum += temp;
        }
    }

    /**
     * 解密
     *
     * @param encodedStr 密文
     * @return 明文
     */
    public String decode(String encodedStr) {
        StringBuilder builder = new StringBuilder(encodedStr);
        decodeInner(builder);
        builder.reverse();
        decodeInner(builder);
        return builder.toString();
    }

    // 解密
    private void decodeInner(StringBuilder builder) {
        int sum = 0;
        for (int i = 0; i < builder.length(); i++) {
            Integer index = shuffledIndexMap.get(builder.charAt(i));
            if (index == null) {
                throw new IllegalArgumentException(String.format("存在非法解密字符'%c'", builder.charAt(i)));
            }
            index -= sum;
            index %= originalChars.length;
            index += originalChars.length;
            index %= originalChars.length;
            builder.setCharAt(i, originalChars[index]);
            sum += index;
        }
    }
}
