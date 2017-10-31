/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-10-31 20:52 创建
 */
package org.antframework.common.util.other;

import org.antframework.common.util.facade.Status;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class EnumUtilsTest {

    @Test
    public void testToEnumName() {
        System.out.println(EnumUtils.toEnumName("aaBbCcDd"));
        System.out.println(EnumUtils.toEnumName("a1Bb2cDd"));
        System.out.println(EnumUtils.toEnumName("a_a_BbCcDd"));
        System.out.println(EnumUtils.toEnumName("AAABBBb"));
        System.out.println(EnumUtils.toEnumName("_aaa_"));
        System.out.println(EnumUtils.toEnumName("_"));
        System.out.println(EnumUtils.toEnumName(""));
        System.out.println(EnumUtils.toEnumName(null));
    }

    @Test
    public void testToCamelCaseName() {
        System.out.println(EnumUtils.toCamelCaseName("AAA_BBB_CCC_DDD"));
        System.out.println(EnumUtils.toCamelCaseName("aaa_bbb_ccc_ddd"));
        System.out.println(EnumUtils.toCamelCaseName("__aaa__bbb_ccc_ddd_"));
        System.out.println(EnumUtils.toCamelCaseName("AAAAAAAA"));
        System.out.println(EnumUtils.toCamelCaseName("aaaaaaaa"));
        System.out.println(EnumUtils.toCamelCaseName("____"));
        System.out.println(EnumUtils.toCamelCaseName(null));
    }

    @Test
    public void testGetEnum() {
        Assert.assertEquals(TradeStatus.GENERATE_NO, EnumUtils.getEnum(TradeStatus.class, "generateNo"));
        Assert.assertEquals(TradeStatus.TRADE_SUCCESS, EnumUtils.getEnum(TradeStatus.class, "tradeSuccess"));
        Assert.assertEquals(TradeStatus.TRADE_FAIL, EnumUtils.getEnum(TradeStatus.class, "tradeFail"));
    }

    @Test
    public void testGetCamelCaseName() {
        Assert.assertEquals("generateNo", EnumUtils.getCamelCaseName(TradeStatus.GENERATE_NO));
        Assert.assertEquals("tradeSuccess", EnumUtils.getCamelCaseName(TradeStatus.TRADE_SUCCESS));
        Assert.assertEquals("tradeFail", EnumUtils.getCamelCaseName(TradeStatus.TRADE_FAIL));
    }

    private enum TradeStatus {
        GENERATE_NO,
        TRADE_SUCCESS,
        TRADE_FAIL
    }
}
