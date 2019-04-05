/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-19 14:52 创建
 */
package org.antframework.common.util.query.annotation.operator.support;

import org.antframework.common.util.query.QueryOperator;

/**
 * 等于null查询条件解析器
 */
public class NullQueryParamParser extends AbstractQueryParamParser {
    @Override
    protected boolean isQueryParam(Object rawValue) {
        return true;
    }

    @Override
    protected QueryOperator getOperator() {
        return QueryOperator.NULL;
    }

    @Override
    protected Object toQueryValue(Object rawValue) {
        return null;
    }
}
