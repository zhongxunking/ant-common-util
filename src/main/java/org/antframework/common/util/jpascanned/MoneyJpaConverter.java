/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-08 15:25 创建
 */
package org.antframework.common.util.jpascanned;

import org.antframework.common.util.money.Money;
import org.antframework.common.util.money.MoneyConverters.LongToMoneyConverter;
import org.antframework.common.util.money.MoneyConverters.MoneyToLongConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Money在jpa场景下的转换器
 */
@Converter(autoApply = true)
public class MoneyJpaConverter implements AttributeConverter<Money, Long> {
    @Override
    public Long convertToDatabaseColumn(Money attribute) {
        if (attribute == null) {
            return null;
        }
        return MoneyToLongConverter.INSTANCE.convert(attribute);
    }

    @Override
    public Money convertToEntityAttribute(Long dbData) {
        if (dbData == null) {
            return null;
        }
        return LongToMoneyConverter.INSTANCE.convert(dbData);
    }
}
