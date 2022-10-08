/*
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-03-31 20:36 创建
 */
package org.antframework.common.util.kit;

/**
 * 限速器
 */
public class RateLimiter {
    // 最小间隔时间（毫秒）
    private final long minInterval;
    // 上次执行时间
    private volatile long lastTime;

    public RateLimiter(long minInterval) {
        if (minInterval < 0) {
            throw new IllegalArgumentException("minInterval不能小于0");
        }
        this.minInterval = minInterval;
        this.lastTime = System.currentTimeMillis() - minInterval;
    }

    /**
     * 是否可以执行
     *
     * @return true 可以；false 不可以
     */
    public boolean can() {
        long currentTime = System.currentTimeMillis();
        if (lastTime > currentTime) {
            // 时钟已被回拨
            lastTime = currentTime;
        }
        return currentTime - lastTime >= minInterval;
    }

    /**
     * 执行
     */
    public void run() {
        lastTime = System.currentTimeMillis();
    }
}
