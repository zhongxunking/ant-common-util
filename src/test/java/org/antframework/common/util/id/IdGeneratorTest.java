/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-18 01:17 创建
 */
package org.antframework.common.util.id;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * id生成器单元测试
 */
@Ignore
public class IdGeneratorTest {
    private IdGenerator idGenerator;

    @Before
    public void init() {
        idGenerator = new IdGenerator(
                PeriodType.HOUR,
                10000,
                1000000L,
                System.getProperty("user.home") + "/a/idGenerator.properties");
    }

    @Test
    public void testGetId() {
        Id id = idGenerator.getId();
    }

    @Test
    public void testGetIdPerformance() {
        long startTime = System.currentTimeMillis();

        int count = 10000000;
        int nullId = 0;
        for (int i = 0; i < count; i++) {
            Id id = idGenerator.getId();
            if (id == null) {
                nullId++;
            }
        }

        long timeCost = System.currentTimeMillis() - startTime;
        System.out.println(String.format("循环次数：%d，id出现null次数：%d，总耗时：%d毫秒，tps：%d", count, nullId, timeCost, (count - nullId) * 1000L / timeCost));
    }
}
