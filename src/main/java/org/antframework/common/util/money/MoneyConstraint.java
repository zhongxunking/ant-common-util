/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-14 14:42 创建
 */
package org.antframework.common.util.money;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Money约束
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MoneyConstraintValidator.class)
public @interface MoneyConstraint {

    String message() default "{org.antframework.common.util.money.MoneyConstraint.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 最小值（单位：分）
     */
    long min() default 1;

    /**
     * 最大值（单位：分）
     */
    long max() default Long.MAX_VALUE;

    /**
     * 是否可以为null（默认不可以）
     */
    boolean nullable() default false;
}
