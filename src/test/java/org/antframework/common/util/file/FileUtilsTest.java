/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-10-23 20:36 创建
 */
package org.antframework.common.util.file;

import org.junit.Test;

/**
 *
 */
public class FileUtilsTest {

    @Test
    public void testCreateFileIfAbsent() {
        String filePath = "/aa/bb/cc.txt";
        FileUtils.createFileIfAbsent(filePath);
    }

    @Test
    public void testCreateDirIfAbsent() {
        String dirPath = "/test/ff/dd";
        FileUtils.createDirIfAbsent(dirPath);
    }
}
