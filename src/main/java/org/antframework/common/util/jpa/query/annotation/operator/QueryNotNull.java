/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 19:25 创建
 */
package org.antframework.common.util.jpa.query.annotation.operator;

import org.antframework.common.util.jpa.query.annotation.QueryParam;
import org.antframework.common.util.jpa.query.annotation.operator.support.NotNullQueryParamParser;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 不等于null查询
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@QueryParam(parser = NotNullQueryParamParser.class)
public @interface QueryNotNull {
    /**
     * 属性名（默认为被注解标注的属性的名称）
     */
    @AliasFor(annotation = QueryParam.class, attribute = "attrName")
    String attrName() default "";
}
