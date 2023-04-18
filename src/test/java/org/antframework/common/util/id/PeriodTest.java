/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2019-01-26 19:41 创建
 */
package org.antframework.common.util.id;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * 周期单元测试
 */
public class PeriodTest {
    @Test
    public void testParsePeriod() throws ParseException {
        Date testDate = parseDate("2017-11-22 23:48:21.250");

        Date hourDate = parseDate("2017-11-22 23:00:00.000");
        Assert.assertEquals(hourDate, new Period(PeriodType.HOUR, testDate).getDate());

        Date dayDate = parseDate("2017-11-22 00:00:00.000");
        Assert.assertEquals(dayDate, new Period(PeriodType.DAY, testDate).getDate());

        Date monthDate = parseDate("2017-11-01 00:00:00.000");
        Assert.assertEquals(monthDate, new Period(PeriodType.MONTH, testDate).getDate());

        Date yearDate = parseDate("2017-01-01 00:00:00.000");
        Assert.assertEquals(yearDate, new Period(PeriodType.YEAR, testDate).getDate());

        Assert.assertNull(new Period(PeriodType.NONE, testDate).getDate());
    }

    @Test
    public void testSubtract() throws ParseException {
        Period hour1 = new Period(PeriodType.HOUR, parseDate("2023-04-18 16:00:00.000"));
        Period hour2 = new Period(PeriodType.HOUR, parseDate("2023-04-18 16:00:00.000"));
        Period hour3 = new Period(PeriodType.HOUR, parseDate("2023-04-19 20:00:00.000"));
        int gap = hour1.subtract(hour2);
        Assert.assertEquals(gap, 0);
        gap = hour1.subtract(hour3);
        Assert.assertEquals(gap, -28);
        gap = hour3.subtract(hour1);
        Assert.assertEquals(gap, 28);

        Period day1 = new Period(PeriodType.DAY, parseDate("2023-04-18 00:00:00.000"));
        Period day2 = new Period(PeriodType.DAY, parseDate("2023-04-18 00:00:00.000"));
        Period day3 = new Period(PeriodType.DAY, parseDate("2023-05-25 00:00:00.000"));
        gap = day1.subtract(day2);
        Assert.assertEquals(gap, 0);
        gap = day1.subtract(day3);
        Assert.assertEquals(gap, -37);
        gap = day3.subtract(day1);
        Assert.assertEquals(gap, 37);

        Period month1 = new Period(PeriodType.MONTH, parseDate("2023-04-01 00:00:00.000"));
        Period month2 = new Period(PeriodType.MONTH, parseDate("2023-04-01 00:00:00.000"));
        Period month3 = new Period(PeriodType.MONTH, parseDate("2024-05-01 00:00:00.000"));
        gap = month1.subtract(month2);
        Assert.assertEquals(gap, 0);
        gap = month1.subtract(month3);
        Assert.assertEquals(gap, -13);
        gap = month3.subtract(month1);
        Assert.assertEquals(gap, 13);

        Period year1 = new Period(PeriodType.YEAR, parseDate("2023-01-01 00:00:00.000"));
        Period year2 = new Period(PeriodType.YEAR, parseDate("2023-01-01 00:00:00.000"));
        Period year3 = new Period(PeriodType.YEAR, parseDate("2034-01-01 00:00:00.000"));
        gap = year1.subtract(year2);
        Assert.assertEquals(gap, 0);
        gap = year1.subtract(year3);
        Assert.assertEquals(gap, -11);
        gap = year3.subtract(year1);
        Assert.assertEquals(gap, 11);
    }

    private static Date parseDate(String date) throws ParseException {
        return DateUtils.parseDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }
}
