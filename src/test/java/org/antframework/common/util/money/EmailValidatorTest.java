/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-25 14:29 创建
 */
package org.antframework.common.util.money;

import org.antframework.common.util.validation.validator.EmailValidator;
import org.junit.Test;

/**
 *
 */
public class EmailValidatorTest {

    @Test
    public void testValidate() {
        String[] emails = {"zhongxunking@163.com"};
        for (String email : emails) {
            System.out.println(EmailValidator.validate(email));
        }
    }

}
