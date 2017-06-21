/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-05-27 16:18 创建
 */
package org.antframework.common.util.money;

import org.junit.Assert;
import org.junit.Test;

/**
 * Money测试类
 */
public class MoneyTest {

    @Test
    public void testCreate() {
        Money money = new Money(10);
        Assert.assertEquals(10, money.getCent());
        money = Money.amount("123.12");
        Assert.assertEquals(12312, money.getCent());
        money = Money.amount("-123.12");
        Assert.assertEquals(-12312, money.getCent());
    }

    @Test
    public void testCalculate() {
        //add
        Money money1 = new Money(20);
        Money money2 = new Money(10);
        Money resultMoney = money1.add(money2);
        Assert.assertEquals(30, resultMoney.getCent());

        // subtract
        money1 = new Money(20);
        money2 = new Money(10);
        resultMoney = money1.subtract(money2);
        Assert.assertEquals(10, resultMoney.getCent());

    }

    @Test
    public void testCompare() {
        // compareTo
        Money money1 = new Money(10);
        Money money2 = new Money(20);
        Assert.assertEquals(-1, money1.compareTo(money2));
        Assert.assertEquals(1, money2.compareTo(money1));
        money1 = new Money(10);
        money2 = new Money(10);
        Assert.assertEquals(0, money1.compareTo(money2));
        Assert.assertEquals(0, money1.compareTo(money1));

        // greaterThan lessThan
        money1 = new Money(20);
        money2 = new Money(10);
        Assert.assertEquals(true, money1.greaterThan(money2));
        Assert.assertEquals(false, money1.lessThan(money2));
        Assert.assertEquals(false, money2.greaterThan(money1));
        Assert.assertEquals(true, money2.lessThan(money1));
        money1 = new Money(10);
        money2 = new Money(10);
        Assert.assertEquals(false, money1.greaterThan(money2));
        Assert.assertEquals(false, money1.lessThan(money2));

        // equals
        money1 = new Money(10);
        money2 = new Money(10);
        Assert.assertEquals(true, money1.equals(money2));
        Assert.assertEquals(true, money1.equals(money1));
        money1 = new Money(20);
        money2 = new Money(10);
        Assert.assertEquals(false, money1.equals(money2));
    }

    @Test
    public void testHashCode() {
        Money money1 = new Money(10);
        Money money2 = new Money(10);
        Assert.assertEquals(money1.hashCode(), money2.hashCode());
        Money money3 = new Money(20);
        Assert.assertNotEquals(money1.hashCode(), money3.hashCode());
    }

    @Test
    public void testToString() {
        Money money = new Money(0);
        Assert.assertEquals("0.00", money.toString());
        money = new Money(1);
        Assert.assertEquals("0.01", money.toString());
        money = new Money(100);
        Assert.assertEquals("1.00", money.toString());
        money = new Money(101);
        Assert.assertEquals("1.01", money.toString());
        money = new Money(-101);
        Assert.assertEquals("-1.01", money.toString());
    }
}
