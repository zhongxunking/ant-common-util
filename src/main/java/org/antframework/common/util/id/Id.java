/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-29 16:24 创建
 */
package org.antframework.common.util.id;

/**
 * id
 */
public class Id {
    // 周期
    private Period period;
    // id
    private long id;

    public Id(Period period, long id) {
        this.period = period;
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public long getId() {
        return id;
    }
}
