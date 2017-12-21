/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 17:41 创建
 */
package org.antframework.common.util.query.annotation.operator;

import org.antframework.common.util.query.QueryOperator;
import org.antframework.common.util.query.annotation.QueryParam;
import org.antframework.common.util.query.annotation.operator.support.NormalQueryParamParser;

import java.lang.annotation.*;

/**
 * 普通查询参数
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@QueryParam(parser = NormalQueryParamParser.class)
public @interface NormalQueryParam {
    /**
     * 操作符
     */
    QueryOperator operator();
}
