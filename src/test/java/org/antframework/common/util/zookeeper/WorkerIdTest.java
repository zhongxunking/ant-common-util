/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-14 16:58 创建
 */
package org.antframework.common.util.zookeeper;

import org.junit.Ignore;
import org.junit.Test;

/**
 * WorkerId单元测试
 */
@Ignore
public class WorkerIdTest {

    @Test
    public void testGetId() {
        int workerId=WorkerId.getId("aaa", new String[]{"localhost:2181"}, "dev2/workerId", System.getProperty("user.home")+"/dd/workerId.properties");
    }
}
