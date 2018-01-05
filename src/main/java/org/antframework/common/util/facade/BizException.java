/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-05 22:51 创建
 */
package org.antframework.common.util.facade;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {
    // 执行结果状态
    private final Status status;
    // 结果码
    private final String code;

    public BizException(Status status, String code, String message) {
        this(status, code, message, null);
    }

    public BizException(Status status, String code, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
    }

    public Status getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
