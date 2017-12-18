/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 17:39 创建
 */
package org.antframework.common.util.jpa.query.annotation.operator.support;

import org.antframework.common.util.jpa.query.QueryOperator;
import org.antframework.common.util.jpa.query.annotation.operator.NormalQueryParam;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;

/**
 * 普通查询参数解析器
 */
public class NormalQueryParamParser extends AbstractQueryParamParser {
    // 操作符
    private QueryOperator operator;

    @Override
    public void initialize(Field field) {
        super.initialize(field);
        this.operator = AnnotatedElementUtils.findMergedAnnotation(field, NormalQueryParam.class).operator();
    }

    @Override
    protected QueryOperator getOperator() {
        return operator;
    }
}
