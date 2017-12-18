/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 18:06 创建
 */
package org.antframework.common.util.jpa.query.annotation.operator.support;

import org.antframework.common.util.jpa.query.QueryOperator;
import org.antframework.common.util.jpa.query.annotation.operator.QueryLike;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;

/**
 * like查询参数解析器
 */
public class LikeQueryParamParser extends AbstractQueryParamParser {
    // 是否左like
    private boolean leftLike;
    // 是否右like
    private boolean rightLike;

    @Override
    public void initialize(Field field) {
        super.initialize(field);
        QueryLike queryLike = AnnotatedElementUtils.findMergedAnnotation(field, QueryLike.class);
        this.leftLike = queryLike.leftLike();
        this.rightLike = queryLike.rightLike();
    }

    @Override
    protected QueryOperator getOperator() {
        return QueryOperator.LIKE;
    }

    @Override
    protected Object toValue(Object rawValue) {
        StringBuilder builder = new StringBuilder(rawValue.toString().length() + 2);
        if (leftLike) {
            builder.append('%');
        }
        builder.append(rawValue.toString());
        if (rightLike) {
            builder.append('%');
        }
        return builder.toString();
    }
}
