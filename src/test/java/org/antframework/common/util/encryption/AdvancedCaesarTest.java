/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-11-04 15:10 创建
 */
package org.antframework.common.util.encryption;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 改进后的凯撒加密单元测试
 */
public class AdvancedCaesarTest {
    private AdvancedCaesar caesar = new AdvancedCaesar("0123456789".toCharArray(), 0);

    @Test
    public void testEncode() {
        for (int i = 0; i < 100; i++) {
            String str = String.format("%05d", i);
            String encodedStr = caesar.encode(str);
            Assert.assertEquals(5, encodedStr.length());
        }
    }

    @Test
    public void testEncode_exception() {
        try {
            String encodedStr = caesar.encode("000a");
            throw new RuntimeException("失败");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testDecode() {
        for (int i = 0; i < 100; i++) {
            String str = String.format("%05d", i);
            String encodedStr = caesar.encode(str);
            String decodedStr = caesar.decode(encodedStr);
            System.out.println(String.format("%s  -->  %s  -->  %s", str, encodedStr, decodedStr));
            Assert.assertEquals(str, decodedStr);
        }
    }

    @Test
    public void testDecode_exception() {
        try {
            String str = caesar.decode("000a");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testReappear() {
        for (int i = 0; i < 100; i++) {
            String str = String.format("%05d", i);
            String encodedStr = caesar.encode(str);
            String encodedStr2 = caesar.encode(str);
            Assert.assertEquals(encodedStr, encodedStr2);

            String decodedStr = caesar.decode(encodedStr);
            String decodedStr2 = caesar.decode(encodedStr);
            Assert.assertEquals(decodedStr, decodedStr2);
        }

        AdvancedCaesar caesar2 = new AdvancedCaesar("0123456789".toCharArray(), 0);
        for (int i = 0; i < 100; i++) {
            String str = String.format("%05d", i);
            String encodedStr = caesar.encode(str);
            String decodedStr = caesar.decode(encodedStr);
            Assert.assertEquals(str, decodedStr);

            String encodedStr2 = caesar2.encode(str);
            String decodedStr2 = caesar2.decode(encodedStr2);
            Assert.assertEquals(str, decodedStr2);

            Assert.assertEquals(encodedStr, encodedStr2);
            Assert.assertEquals(decodedStr, decodedStr2);
        }
    }

    @Test
    public void testRobust() {
        int amount = 100000;
        int length = 5;

        Set<String> encodedStrs = new HashSet<>(amount);
        Set<String> decodedStrs = new HashSet<>(amount);
        for (int i = 0; i < amount; i++) {
            String str = String.format("%0" + length + "d", i);
            String encodedStr = caesar.encode(str);
            String decodedStr = caesar.decode(encodedStr);

            encodedStrs.add(encodedStr);
            decodedStrs.add(decodedStr);
        }

        Assert.assertEquals(amount, encodedStrs.size());
        Assert.assertEquals(amount, decodedStrs.size());
    }
}
