/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-06 16:06 创建
 */
package org.antframework.common.util.id;

import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * 周期
 */
@Getter
public final class Period implements Comparable<Period>, Serializable {
    // 周期类型
    private final PeriodType type;
    // 周期时间
    private final Date date;

    /**
     * 构造周期
     *
     * @param type 周期类型
     * @param date 周期时间
     */
    public Period(PeriodType type, Date date) {
        Objects.requireNonNull(type, "周期类型不能为null");
        this.type = type;
        this.date = parseDate(type, date);
    }

    /**
     * 周期增加
     *
     * @param length 增加长度
     * @return 增加后的周期
     */
    public Period grow(int length) {
        Date date;
        switch (type) {
            case HOUR:
                date = DateUtils.addHours(this.date, length);
                break;
            case DAY:
                date = DateUtils.addDays(this.date, length);
                break;
            case MONTH:
                date = DateUtils.addMonths(this.date, length);
                break;
            case YEAR:
                date = DateUtils.addYears(this.date, length);
                break;
            case NONE:
                if (length != 0) {
                    throw new IllegalArgumentException("周期类型为NONE的周期不能进行增加");
                }
                date = null;
                break;
            default:
                throw new IllegalArgumentException("无法识别的周期类型：" + type);
        }

        return new Period(type, date);
    }

    /**
     * 相减计算出周期差额
     *
     * @param other 另一个周期
     * @return 周期差额
     */
    public int subtract(Period other) {
        int sign = compareTo(other);
        if (sign == 0) {
            return 0;
        }

        Period min = sign < 0 ? this : other;
        Period max = sign > 0 ? this : other;

        int gap = 0;
        Period anchor = min;
        while (anchor.compareTo(max) < 0) {
            gap++;
            anchor = anchor.grow(1);
        }

        if (sign < 0) {
            gap = -gap;
        }
        return gap;
    }

    @Override
    public int compareTo(Period other) {
        if (type != other.getType()) {
            throw new IllegalArgumentException("不同类型的周期不能进行比较");
        }
        if (date == other.getDate()) {
            return 0;
        }
        return date.compareTo(other.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Period)) {
            return false;
        }
        Period other = (Period) obj;
        return type == other.getType() && compareTo(other) == 0;
    }

    @Override
    public String toString() {
        switch (type) {
            case HOUR:
                return DateFormatUtils.format(date, "yyyyMMddHH");
            case DAY:
                return DateFormatUtils.format(date, "yyyyMMdd");
            case MONTH:
                return DateFormatUtils.format(date, "yyyyMM");
            case YEAR:
                return DateFormatUtils.format(date, "yyyy");
            case NONE:
                return "";
            default:
                throw new IllegalArgumentException("无法识别的周期类型：" + type);
        }
    }

    // 解析出周期时间
    private static Date parseDate(PeriodType type, Date date) {
        if (type == PeriodType.NONE) {
            return null;
        } else {
            Objects.requireNonNull(date, String.format("周期类型为%s，则周期时间不能为null", type));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.setTime(date);

        switch (type) {
            case YEAR:
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
            case MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            case DAY:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case HOUR:
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
            default:
                throw new IllegalArgumentException("无法识别的周期类型：" + type);
        }
        return calendar.getTime();
    }
}
