/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-02 14:18 创建
 */
package org.antframework.common.util.facade;

import lombok.Getter;
import lombok.Setter;
import org.antframework.common.util.tostring.hidedetail.HideDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽象分页result（所有分页result的父类）
 */
@Getter
public abstract class AbstractQueryResult<T extends Serializable> extends AbstractResult {
    // 记录总数
    @Setter
    private long totalCount;
    // 查询出的当前页详细数据
    @HideDetail
    private final List<T> infos = new ArrayList<>();

    public void addInfo(T info) {
        infos.add(info);
    }
}
