/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-19 09:54 创建
 */
package org.antframework.common.util.jpa.query;

import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * SpecificationUtils单元测试
 */
public class SpecificationUtilsTest {

    @Test
    public void testParse() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(new QueryParam("username", QueryOperator.LIKE, "zhongxun"));
        queryParams.add(new QueryParam("age", QueryOperator.GTE, 20));
        queryParams.add(new QueryParam("address", QueryOperator.EQ, "shanghai"));

        Specification specification = SpecificationUtils.parse(queryParams);
    }
}
