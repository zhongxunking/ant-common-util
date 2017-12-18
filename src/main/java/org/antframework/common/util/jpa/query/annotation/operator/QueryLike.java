/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 18:03 创建
 */
package org.antframework.common.util.jpa.query.annotation.operator;

import org.antframework.common.util.jpa.query.annotation.QueryParam;
import org.antframework.common.util.jpa.query.annotation.operator.support.LikeQueryParamParser;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * like查询
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@QueryParam(parser = LikeQueryParamParser.class)
public @interface QueryLike {
    /**
     * 属性名（默认为被注解标注的属性的名称）
     */
    @AliasFor(annotation = QueryParam.class, attribute = "attrName")
    String attrName() default "";

    /**
     * 是否左like（默认true）
     */
    boolean leftLike() default true;

    /**
     * 是否右like（默认true）
     */
    boolean rightLike() default true;
}
