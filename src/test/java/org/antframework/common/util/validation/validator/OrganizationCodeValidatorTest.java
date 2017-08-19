/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-25 16:02 创建
 */
package org.antframework.common.util.validation.validator;

import org.junit.Test;

/**
 *
 */
public class OrganizationCodeValidatorTest {

    @Test
    public void testValidate() {
        String[] organizationCodes = {"91371600MA3DQC9148",
                "91460200MA5RHCFK5N",
                "92131127MA08L4NM6X",
                "92441802MA4WLLX645",
                "91371428MA3DQ8PY54"
        };
        for (String organizationCode : organizationCodes) {
            System.out.println(OrganizationCodeValidator.validate(organizationCode));
        }
    }

}
