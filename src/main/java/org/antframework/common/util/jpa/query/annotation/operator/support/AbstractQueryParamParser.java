/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 16:51 创建
 */
package org.antframework.common.util.jpa.query.annotation.operator.support;

import org.antframework.common.util.jpa.query.QueryOperator;
import org.antframework.common.util.jpa.query.QueryParam;
import org.antframework.common.util.jpa.query.annotation.QueryParamParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 抽象查询参数解析器
 */
public abstract class AbstractQueryParamParser implements QueryParamParser {
    // 被注解标注的属性
    private Field field;
    // 被查询的属性名
    private String attrName;

    @Override
    public void init(Field field) {
        this.field = field;
        attrName = AnnotatedElementUtils.findMergedAnnotation(field, org.antframework.common.util.jpa.query.annotation.QueryParam.class).attrName();
        if (StringUtils.isEmpty(attrName)) {
            attrName = field.getName();
        }
    }

    @Override
    public QueryParam parse(Object obj) {
        Object rawValue = ReflectionUtils.getField(field, obj);
        return new QueryParam(attrName, getOperator(), toValue(rawValue));
    }

    // 获取操作符
    protected abstract QueryOperator getOperator();

    // 转换为查询value
    protected Object toValue(Object rawValue) {
        return rawValue;
    }
}
