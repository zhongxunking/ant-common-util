/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 14:40 创建
 */
package org.antframework.common.util.query;

/**
 * 查询操作符
 */
public enum QueryOperator {
    // 等于
    EQ,
    // 不等于
    NOTEQ,
    // 大于
    GT,
    // 大于等于
    GTE,
    // 小于
    LT,
    // 小于等于
    LTE,
    // like操作
    LIKE,
    // 等于null
    NULL,
    // 不等于null
    NOTNULL,
    // in操作
    IN
}
