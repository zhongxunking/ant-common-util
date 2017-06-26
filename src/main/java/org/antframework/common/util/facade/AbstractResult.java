/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-01 19:25 创建
 */
package org.antframework.common.util.facade;

import org.antframework.common.util.tostring.ToString;

import java.io.Serializable;

/**
 * 抽象result（所有result的父类）
 */
public abstract class AbstractResult implements Serializable {
    // 状态
    private Status status;
    // 结果码
    private String code;
    // 描述信息
    private String message;

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
