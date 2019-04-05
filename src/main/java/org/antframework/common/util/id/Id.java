/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-29 16:24 创建
 */
package org.antframework.common.util.id;

import lombok.Getter;
import org.antframework.common.util.tostring.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * id
 */
@Getter
public final class Id implements Comparable<Id>, Serializable {
    // 周期
    private final Period period;
    // id
    private final long id;

    /**
     * 构造id
     *
     * @param period 周期
     * @param id     id
     */
    public Id(Period period, long id) {
        Objects.requireNonNull(period, "id的周期不能为null");
        this.period = period;
        this.id = id;
    }

    @Override
    public int compareTo(Id other) {
        int result = period.compareTo(other.period);
        if (result == 0) {
            result = Long.compare(id, other.id);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(period, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Id)) {
            return false;
        }
        Id other = (Id) obj;
        return period.equals(other.period) && id == other.id;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
