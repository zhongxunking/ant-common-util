/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-27 20:54 创建
 */
package org.antframework.common.util.tostring.hidedetail;

import org.antframework.common.util.tostring.FieldFormat;

import java.lang.annotation.*;

/**
 * 数组、集合、Map类型属性ToString时隐藏细节
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldFormat(formatter = HideDetailFieldFormatter.class)
public @interface HideDetail {
}
