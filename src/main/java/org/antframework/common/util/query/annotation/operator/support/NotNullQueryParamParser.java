/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-19 14:51 创建
 */
package org.antframework.common.util.query.annotation.operator.support;

import org.antframework.common.util.query.QueryOperator;

/**
 * 不等于null查询条件解析器
 */
public class NotNullQueryParamParser extends AbstractQueryParamParser {
    @Override
    protected boolean isQueryParam(Object rawValue) {
        return true;
    }

    @Override
    protected QueryOperator getOperator() {
        return QueryOperator.NOTNULL;
    }

    @Override
    protected Object toQueryValue(Object rawValue) {
        return null;
    }
}
