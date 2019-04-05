/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-12 10:36 创建
 */
package org.antframework.common.util.money;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

/**
 * money转换器聚合类
 */
public class MoneyConverters {
    /**
     * 获取Money的所有转换器
     */
    public static List<Converter> getConverters() {
        List<Converter> converters = new ArrayList<>();
        converters.add(StringToMoneyConverter.INSTANCE);
        converters.add(LongToMoneyConverter.INSTANCE);
        converters.add(MoneyToLongConverter.INSTANCE);

        return converters;
    }

    /**
     * String转Money转换器
     */
    public enum StringToMoneyConverter implements Converter<String, Money> {

        INSTANCE;

        @Override
        public Money convert(String source) {
            return Money.amount(source);
        }
    }

    /**
     * Long转Money转换器
     */
    public enum LongToMoneyConverter implements Converter<Long, Money> {

        INSTANCE;

        @Override
        public Money convert(Long source) {
            return new Money(source);
        }
    }

    /**
     * Money转Long转换器
     */
    public enum MoneyToLongConverter implements Converter<Money, Long> {

        INSTANCE;

        @Override
        public Long convert(Money source) {
            return source.getCent();
        }
    }
}
