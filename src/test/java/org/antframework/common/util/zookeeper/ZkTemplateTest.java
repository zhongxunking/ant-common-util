/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-07 19:45 创建
 */
package org.antframework.common.util.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 *
 */
@Ignore
public class ZkTemplateTest {
    private static final String zkUrl = "localhost:2181";

    private ZkTemplate zkTemplate;

    @Before
    public void init() throws InterruptedException {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkUrl)
                .namespace("ant-common-util/test")
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        zkClient.start();
        if (!zkClient.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
            throw new RuntimeException(String.format("连接zookeeper[%s]失败", zkUrl));
        }

        zkTemplate = new ZkTemplate(zkClient);
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
}
