/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 14:32 创建
 */
package org.antframework.common.util.jpa.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询参数
 */
public class QueryParam {
    /**
     * 操作符与属性名之间的分隔符
     */
    public static final char OPERATOR_ATTRNAME_SEPARATOR = '_';

    // 属性名
    private String attrName;
    // 操作符
    private QueryOperator operator;
    // 值
    private Object value;

    public QueryParam(String attrName, QueryOperator operator, Object value) {
        Assert.hasText(attrName, "无效的属性名：" + attrName);
        Assert.notNull(operator, "操作符不能为null");
        this.attrName = attrName;
        this.operator = operator;
        this.value = value;
    }

    public String getAttrName() {
        return attrName;
    }

    public QueryOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    /**
     * 根据map解析出查询参数
     *
     * @param queryMap 需被解析的map
     *                 Map的key格式：操作符_属性名，比如：GT_age表示age大于指定值。操作符参考：{@link QueryOperator}。 如果嵌套的属性，则以“.”隔开，比如：GT_user.age表示以属性user的属性age大于指定值
     *                 Map的value格式：非IN操作符，就是指定的value；IN操作符，value必须得是数组或Collection类型
     * @return 解析得到的查询参数
     */
    public static List<QueryParam> parse(Map<String, Object> queryMap) {
        List<QueryParam> queryParams = new ArrayList<>(queryMap.size());
        for (String key : queryMap.keySet()) {
            String[] names = StringUtils.splitByWholeSeparatorPreserveAllTokens(key, Character.toString(OPERATOR_ATTRNAME_SEPARATOR), 2);
            if (names.length != 2) {
                throw new IllegalArgumentException("非法查询参数：" + key);
            }
            queryParams.add(new QueryParam(names[1], QueryOperator.valueOf(names[0]), queryMap.get(key)));
        }

        return queryParams;
    }
}
