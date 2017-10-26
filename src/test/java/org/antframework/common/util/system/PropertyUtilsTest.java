/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-10-26 15:44 创建
 */
package org.antframework.common.util.system;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PropertyUtilsTest {

    @Test
    public void testGetProperty() {
        Assert.assertEquals(8, PropertyUtils.toEnvKeys("aa.bb-cc.dd").length);
        Assert.assertEquals(4, PropertyUtils.toEnvKeys("aa.bb.cc.dd").length);
        Assert.assertEquals(2, PropertyUtils.toEnvKeys("aabbccdd").length);
        Assert.assertEquals(1, PropertyUtils.toEnvKeys("AABBCCDD").length);
        Assert.assertEquals(null, PropertyUtils.getProperty("aa.bb-cc.dd"));
        Assert.assertEquals("myvalue", PropertyUtils.getProperty("aa.bb-cc.dd", "myvalue"));
    }
}
