/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-13 12:54 创建
 */
package org.antframework.common.util.zookeeper;

import org.antframework.common.util.file.MapFile;
import org.antframework.common.util.tostring.ToString;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * workerId
 */
public class WorkerId {
    private static final Logger logger = LoggerFactory.getLogger(WorkerId.class);

    /**
     * 获取workerId（本方法不适合被频繁调用，因为每次调用都会连接zookeeper并释放）
     *
     * @param worker        worker（每个worker都不一样）
     * @param zkUrls        zookeeper链接
     * @param nodePath      worker在zookeeper上注册节点的父路径
     * @param cacheFilePath 缓存文件路径（null表示不使用缓存文件）
     * @return workerId
     */
    public static int getId(String worker, String[] zkUrls, String nodePath, String cacheFilePath) {
        MapFile cacheFile = cacheFilePath == null ? null : new MapFile(cacheFilePath);
        String id;
        boolean fromZk = true;
        try {
            id = getIdFromZk(worker, zkUrls, nodePath);
        } catch (Throwable e) {
            logger.error("从zookeeper上读取workerId出错:{}", e);
            if (cacheFile != null) {
                logger.warn("尝试从缓存文件读取workerId");
                id = cacheFile.read(worker);
                if (id == null) {
                    throw new IllegalStateException("不存在缓存文件或缓存文件内无workerId");
                }
                fromZk = false;
            } else {
                return ExceptionUtils.rethrow(e);
            }
        }
        if (fromZk && cacheFile != null) {
            cacheFile.store(worker, id);
        }
        return Integer.parseInt(id);
    }

    // 从zookeeper获取id
    private static String getIdFromZk(String worker, String[] zkUrls, String nodePath) throws InterruptedException {
        ZkTemplate zkTemplate = ZkTemplate.create(zkUrls, "");
        try {
            if (!zkTemplate.getZkClient().getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                throw new IllegalStateException(String.format("链接zookeeper%s失败", ToString.toString(zkUrls)));
            }
            List<String> nodes = zkTemplate.findChildren(nodePath, String.format("^%s-[0-9]+$", worker));
            if (nodes.size() <= 0) {
                zkTemplate.createNode(ZkTemplate.buildPath(nodePath, worker + "-"), CreateMode.PERSISTENT_SEQUENTIAL);
                nodes = zkTemplate.findChildren(nodePath, String.format("^%s-[0-9]+$", worker));
            }
            if (nodes.size() != 1) {
                throw new IllegalStateException(String.format("worker[%s]在zookeeper上注册的数量[%d]不为1", worker, nodes.size()));
            }
            String[] parts = StringUtils.split(nodes.get(0), '-');
            return parts[parts.length - 1];
        } finally {
            zkTemplate.close();
        }
    }
}
