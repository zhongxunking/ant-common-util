/*
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2023-03-01 21:34 创建
 */
package org.antframework.common.util.kit;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * 可排序的实体中心
 */
@AllArgsConstructor
public class SortableHub<T> {
    // 实体比较器
    private final Comparator<T> comparator;
    // 实体集
    private volatile List<T> entities = new ArrayList<>(0);

    /**
     * 新增实体
     *
     * @param entity 实体
     */
    public synchronized void add(T entity) {
        if (entities.contains(entity)) {
            return;
        }
        List<T> newEntities = new ArrayList<>(entities.size() + 1);
        newEntities.addAll(entities);
        newEntities.add(entity);
        Collections.sort(newEntities, comparator);

        entities = newEntities;
    }

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    public synchronized void remove(T entity) {
        if (!entities.contains(entity)) {
            return;
        }
        List<T> newEntities = new ArrayList<>(entities);
        newEntities.remove(entity);
        entities = newEntities;
    }

    /**
     * 遍历实体
     *
     * @param consumer 遍历实体的处理器
     */
    public void foreach(Consumer<T> consumer) {
        for (T entity : entities) {
            consumer.accept(entity);
        }
    }
}
