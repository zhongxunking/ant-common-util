/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 15:19 创建
 */
package org.antframework.common.util.query.annotation;

import java.lang.annotation.*;

/**
 * 查询参数父注解
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryParam {
    /**
     * 属性名（默认为被注解标注的属性的名称）
     */
    String attrName() default "";

    /**
     * 查询参数解析器
     */
    Class<? extends QueryParamParser> parser();
}
