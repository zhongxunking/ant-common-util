/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-01 19:22 创建
 */
package org.antframework.common.util.facade;

import org.antframework.common.util.validation.AntValidation;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Set;

/**
 * 抽象order（所有order的父类）
 */
public abstract class AbstractOrder implements Serializable {
    /**
     * 校验
     */
    public void check() {
        checkWithGroups();
    }

    /**
     * 分组校验
     *
     * @param groups 分组
     * @throws IllegalArgumentException 如果校验不通过
     */
    public final void checkWithGroups(Class... groups) {
        Set<ConstraintViolation<AbstractOrder>> violations = AntValidation.getValidator().validate(this, groups);
        if (violations.size() > 0) {
            // 校验不通过，构建错误信息
            StringBuilder errMsg = new StringBuilder();
            for (ConstraintViolation<AbstractOrder> violation : violations) {
                errMsg.append(violation.getPropertyPath().toString());
                errMsg.append(violation.getMessage() + ";");
            }
            errMsg.deleteCharAt(errMsg.length() - 1);
            throw new IllegalArgumentException(errMsg.toString());
        }
    }
}
