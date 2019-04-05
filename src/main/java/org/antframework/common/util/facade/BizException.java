/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-05 22:51 创建
 */
package org.antframework.common.util.facade;

import lombok.Getter;

/**
 * 业务异常
 */
@Getter
public class BizException extends RuntimeException {
    // 结果状态
    private final Status status;
    // 结果码
    private final String code;

    /**
     * 创建业务异常
     *
     * @param status  结果状态
     * @param code    结果码
     * @param message 描述
     */
    public BizException(Status status, String code, String message) {
        this(status, code, message, null);
    }

    /**
     * 创建业务异常
     *
     * @param status  结果状态
     * @param code    结果码
     * @param message 描述
     * @param cause   原因
     */
    public BizException(Status status, String code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }
}
