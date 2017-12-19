/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 16:42 创建
 */
package org.antframework.common.util.jpa.query.annotation;

import org.antframework.common.util.jpa.query.QueryParam;

import java.lang.reflect.Field;

/**
 * 查询参数解析器
 */
public interface QueryParamParser {
    /**
     * 初始化（一个实例只被执行一次）
     *
     * @param field 被标注的属性
     */
    void init(Field field);

    /**
     * 解析出查询参数（此方法会被并发调用，实现类需保证线程安全）
     *
     * @param obj 被标注的对象
     * @return null表示没有查询参数
     */
    QueryParam parse(Object obj);
}
