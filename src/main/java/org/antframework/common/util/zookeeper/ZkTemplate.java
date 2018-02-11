/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-07 16:46 创建
 */
package org.antframework.common.util.zookeeper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * zookeeper操作类
 */
public class ZkTemplate {
    /**
     * 路径中节点分隔符
     */
    public static final char NODE_SEPARATOR = '/';
    /**
     * zookeeper链接地址分隔符
     */
    public static final char ZK_URL_SEPARATOR = ',';

    /**
     * 创建ZkTemplate
     *
     * @param zkUrls    zookeeper地址（ip:端口）
     * @param namespace 命名空间
     * @return zkTemplate
     */
    public static ZkTemplate create(String[] zkUrls, String namespace) {
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(StringUtils.join(zkUrls, ZK_URL_SEPARATOR))
                .namespace(namespace)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        zkClient.start();

        return new ZkTemplate(zkClient);
    }

    /**
     * 构建路径
     *
     * @param pathParts 路径片段
     */
    public static String buildPath(String... pathParts) {
        if (pathParts == null) {
            return null;
        }
        StringBuilder pathBuilder = new StringBuilder();
        for (String pathPart : pathParts) {
            if (!pathPart.startsWith(Character.toString(NODE_SEPARATOR))) {
                pathBuilder.append(NODE_SEPARATOR);
            }
            pathBuilder.append(pathPart);
            if (pathBuilder.length() > 0 && pathBuilder.charAt(pathBuilder.length() - 1) == NODE_SEPARATOR) {
                pathBuilder.deleteCharAt(pathBuilder.length() - 1);
            }
        }
        if (pathBuilder.length() <= 0) {
            pathBuilder.append(NODE_SEPARATOR);
        }
        return pathBuilder.toString();
    }

    // zookeeper客户端
    private CuratorFramework zkClient;

    public ZkTemplate(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    /**
     * 校验节点是否存在
     *
     * @param path 节点路径
     */
    public boolean checkExists(String path) {
        try {
            return zkClient.checkExists().forPath(path) != null;
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 创建节点（路径中任何父节点如果不存在，则会创建CreateMode.PERSISTENT类型的该父节点；只有路径中最有一个节点才会使用mode参数）
     *
     * @param path 节点路径
     * @param mode 节点类型
     * @return 被创建的节点路径
     */
    public String createNode(String path, CreateMode mode) {
        try {
            String[] pathParts = StringUtils.split(path, NODE_SEPARATOR);
            if (pathParts.length <= 0) {
                return path;
            }
            // 创建路径中的父节点
            StringBuilder pathBuilder = new StringBuilder();
            for (int i = 0; i < pathParts.length - 1; i++) {
                pathBuilder.append(NODE_SEPARATOR).append(pathParts[i]);
                if (!checkExists(pathBuilder.toString())) {
                    zkClient.create().withMode(CreateMode.PERSISTENT).forPath(pathBuilder.toString());
                }
            }
            // 创建路径中的最后一个节点
            pathBuilder.append(NODE_SEPARATOR).append(pathParts[pathParts.length - 1]);
            if (mode.isSequential() || !checkExists(pathBuilder.toString())) {
                return zkClient.create().withMode(mode).forPath(pathBuilder.toString());
            }
            return path;
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 删除节点（如果该节点存在子节点，则会递归删除子节点）
     *
     * @param path 节点路径
     */
    public void deleteNode(String path) {
        try {
            if (!checkExists(path)) {
                return;
            }
            for (String child : getChildren(path)) {
                deleteNode(buildPath(path, child));
            }
            zkClient.delete().forPath(path);
        } catch (Exception e) {
            ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 获取节点数据（节点不存在会抛异常）
     *
     * @param path 节点路径
     * @return 节点数据
     */
    public byte[] getData(String path) {
        try {
            return zkClient.getData().forPath(path);
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 给节点设置数据（节点不存在会抛异常）
     *
     * @param path 节点路径
     * @param data 数据
     */
    public void setData(String path, byte[] data) {
        try {
            zkClient.setData().forPath(path, data);
        } catch (Exception e) {
            ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 监听节点
     *
     * @param path             节点路径
     * @param initCallListener 初始化时是否调用监听器
     * @param listeners        监听器
     * @return 底层NodeCache
     */
    public NodeCache listenNode(String path, boolean initCallListener, NodeListener... listeners) {
        try {
            NodeCache nodeCache = new NodeCache(zkClient, path);
            for (NodeListener listener : listeners) {
                listener.init(nodeCache);
                nodeCache.getListenable().addListener(listener);
            }
            nodeCache.start(!initCallListener);
            return nodeCache;
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 获取子节点
     *
     * @param path 父节点路径
     * @return 子节点名称（如果父节点路径不存在则返回null）
     */
    public List<String> getChildren(String path) {
        try {
            if (!checkExists(path)) {
                return null;
            }
            return zkClient.getChildren().forPath(path);
        } catch (Exception e) {
            return ExceptionUtils.rethrow(e);
        }
    }

    /**
     * 查找子节点
     *
     * @param path         父节点路径
     * @param childPattern 子节点正则表达式
     * @return 匹配的子节点（如果父节点路径不存在则返回null）
     */
    public List<String> findChildren(String path, String childPattern) {
        List<String> children = getChildren(path);
        if (children == null) {
            return null;
        }
        List<String> matchedChildren = new ArrayList<>();
        Pattern pattern = Pattern.compile(childPattern);
        for (String child : children) {
            if (pattern.matcher(child).matches()) {
                matchedChildren.add(child);
            }
        }
        return matchedChildren;
    }

    /**
     * 获取zkClient
     */
    public CuratorFramework getZkClient() {
        return zkClient;
    }

    /**
     * 关闭（释放zookeeper链接）
     */
    public void close() {
        zkClient.close();
    }

    /**
     * 节点监听器
     */
    public interface NodeListener extends NodeCacheListener {
        /**
         * 初始化
         */
        default void init(NodeCache nodeCache) {
        }
    }
}
