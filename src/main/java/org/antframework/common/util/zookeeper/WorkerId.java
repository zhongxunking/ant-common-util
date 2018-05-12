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

import java.nio.charset.Charset;

/**
 * workerId
 */
public final class WorkerId {
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
            logger.error("从zookeeper上读取workerId出错：{}", e.getMessage());
            if (cacheFile != null) {
                logger.warn("尝试从缓存文件读取workerId");
                id = cacheFile.read(worker);
                if (id == null) {
                    throw new IllegalStateException(String.format("不存在缓存文件[%s]或缓存文件内无worker[%s]的workerId", cacheFile.getFilePath(), worker));
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
        ZkTemplate zkTemplate = ZkTemplate.create(zkUrls, null);
        try {
            if (!zkTemplate.getZkClient().getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                throw new IllegalStateException(String.format("链接zookeeper%s失败", ToString.toString(zkUrls)));
            }
            zkTemplate.createNode(nodePath, CreateMode.PERSISTENT);
            // 构建worker节点路径
            String workerPath = ZkTemplate.buildPath(nodePath, worker);
            // 判断该worker是否已经在zookeeper上注册过
            if (!zkTemplate.checkExists(workerPath)) {
                // 创建worker的顺序节点
                String createdPath = zkTemplate.createNode(workerPath + '-', CreateMode.PERSISTENT_SEQUENTIAL);
                String[] parts = StringUtils.split(createdPath, '-');
                // 创建worker节点
                zkTemplate.createNode(workerPath, CreateMode.PERSISTENT);
                // 设置workerId
                zkTemplate.setData(workerPath, parts[parts.length - 1].getBytes(Charset.forName("utf-8")));
            }

            return new String(zkTemplate.getData(workerPath), Charset.forName("utf-8"));
        } finally {
            zkTemplate.close();
        }
    }
}
