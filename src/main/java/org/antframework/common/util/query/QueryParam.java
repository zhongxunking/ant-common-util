/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 14:32 创建
 */
package org.antframework.common.util.query;

import lombok.Getter;
import org.springframework.util.Assert;

/**
 * 查询参数
 */
@Getter
public final class QueryParam {
    // 属性名
    private final String attrName;
    // 操作符
    private final QueryOperator operator;
    // 值
    private final Object value;

    public QueryParam(String attrName, QueryOperator operator, Object value) {
        Assert.hasText(attrName, "无效的属性名：" + attrName);
        Assert.notNull(operator, "操作符不能为null");
        this.attrName = attrName;
        this.operator = operator;
        this.value = value;
    }
}
