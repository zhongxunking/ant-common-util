/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-25 15:32 创建
 */
package org.antframework.common.util.validation.validator;

import org.junit.Test;

/**
 *
 */
public class BankCarNoValidatorTest {

    @Test
    public void testValidate() {
        String[] bankCardNos = {
                "6228480402564890018",
                "6228480402637874213",
                "6228481552887309119"};
        for (String bankCardNo : bankCardNos) {
            System.out.println(BankCarNoValidator.validate(bankCardNo));
        }
    }

}
