/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-18 14:15 创建
 */
package org.antframework.common.util.validation;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * 校验JSR303
 */
public class AntValidation {
    // JSR303校验器
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 获取JSR303校验器
     */
    public static Validator getValidator() {
        return validator;
    }
}
