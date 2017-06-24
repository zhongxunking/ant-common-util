/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-24 23:31 创建
 */
package org.antframework.common.util.money;

import org.antframework.common.util.validation.validator.MobileNoValidator;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class MobileNoValidatorTest {

    @Test
    public void testValidate() {
        String[] mobileNos = {"18949141125",
                "18255082662",
                "15505505515",
                "13755072771",
                "15066063937"};
        for (String mobileNo : mobileNos) {
            Assert.assertTrue(MobileNoValidator.validate(mobileNo));
        }
    }

}
