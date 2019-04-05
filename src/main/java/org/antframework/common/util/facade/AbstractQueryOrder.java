/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-02 14:11 创建
 */
package org.antframework.common.util.facade;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * 抽象分页查询order（所有分页查询order的父类）
 */
@Getter
@Setter
public abstract class AbstractQueryOrder extends AbstractOrder {
    // 页码（从1开始）
    @Min(1)
    private int pageNo;
    // 每页大小
    @Min(1)
    private int pageSize;
}
