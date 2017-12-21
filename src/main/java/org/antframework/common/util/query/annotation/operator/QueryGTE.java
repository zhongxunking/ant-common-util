/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 17:53 创建
 */
package org.antframework.common.util.query.annotation.operator;

import org.antframework.common.util.query.QueryOperator;
import org.antframework.common.util.query.annotation.QueryParam;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 大于等于查询
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@NormalQueryParam(operator = QueryOperator.GTE)
public @interface QueryGTE {
    /**
     * 属性名（默认为被注解标注的属性的名称）
     */
    @AliasFor(annotation = QueryParam.class, attribute = "attrName")
    String attrName() default "";
}
