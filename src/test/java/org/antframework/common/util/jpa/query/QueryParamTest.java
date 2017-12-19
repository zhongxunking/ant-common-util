/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-19 09:40 创建
 */
package org.antframework.common.util.jpa.query;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QueryParam单元测试
 */
public class QueryParamTest {

    @Test
    public void testParse() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("EQ_age", 10);
        queryMap.put("LIKE_user_name", "zhongxun");
        queryMap.put("GTE__grade", 60);

        List<QueryParam> queryParams = QueryParam.parse(queryMap);
        Assert.assertSame(3, queryParams.size());
    }
}
