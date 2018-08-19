/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-07 19:45 创建
 */
package org.antframework.common.util.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.List;

/**
 * ZkTemplate单元测试
 */
@Ignore
public class ZkTemplateTest {
    private ZkTemplate zkTemplate;

    @Before
    public void init() {
        zkTemplate = ZkTemplate.create(new String[]{"localhost:2181"}, "ant-common-util/test");
    }

    @After
    public void close() {
        zkTemplate.close();
    }

    @Test
    public void testCreateNode() throws Exception {
        zkTemplate.createNode("/dev/scbfund", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund1/aa", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund1/bb", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund1/cc", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund1/dd", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund2", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund2/aa", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund2/bb", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund2/cc", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund2/dd", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund3", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund4", CreateMode.PERSISTENT);
        zkTemplate.createNode("/dev/scbfund5", CreateMode.PERSISTENT);
    }

    @Test
    public void testDeleteNode() {
        zkTemplate.deleteNode("/dev");
    }

    @Test
    public void testGetData() {
        zkTemplate.createNode("/a/b/c", CreateMode.PERSISTENT);
        zkTemplate.setData("/a/b/c", "hello".getBytes(Charset.forName("utf-8")));
        byte[] data = zkTemplate.getData("/a/b/c");
        String hello = new String(data, Charset.forName("utf-8"));
    }

    @Test
    public void testSetData() {
        zkTemplate.setData("/dev/scbfund", "abcd".getBytes());
    }

    @Test
    public void testGetChildren() {
        List<String> children = zkTemplate.getChildren("/dev/scbfund");
    }

    @Test
    public void testFindChildren() {
        List<String> matchedChildren = zkTemplate.findChildren("/dev", "^[a-z]*[1-9]$");
    }

    @Test
    public void testGetZkUrls() {
        String[] zkUrls = zkTemplate.getZkUrls();
    }

    @Test
    public void testGetNamespace() {
        String namespace = zkTemplate.getNamespace();
    }
}
