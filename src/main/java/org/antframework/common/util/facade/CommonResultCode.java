/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-08-19 14:42 创建
 */
package org.antframework.common.util.facade;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 常用结果码
 */
@AllArgsConstructor
@Getter
public enum CommonResultCode {

    SUCCESS("common-0000", "成功"),

    UNKNOWN_ERROR("common-0001", "未知错误"),

    INVALID_PARAMETER("common-0002", "请求参数非法"),

    ILLEGAL_STATE("common-0003", "非法内部状态"),

    UNAUTHORIZED("common-0004", "未授权"),;

    // 结果码
    private final String code;
    // 描述
    private final String message;
}
