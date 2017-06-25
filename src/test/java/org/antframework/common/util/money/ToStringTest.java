/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-22 22:03 创建
 */
package org.antframework.common.util.money;

import org.antframework.common.util.tostring.ToString;
import org.antframework.common.util.tostring.format.Mask;
import org.junit.Test;

/**
 *
 */
public class ToStringTest {

    @Test
    public void testMask() {
        User user = new User("zhongxun",
                17,
                "123456",
                "15082119920706049X",
                "18949141125",
                "abcde@163.com",
                "6228480402637874213",
                "91371600MA3DQC9148");
        System.out.println(user);
    }

    public static class User {
        private String name;
        private int age;
        @Mask(allMask = true)
        private String password;
        @Mask
        private String certNo;
        @Mask
        private String mobileNo;
        @Mask
        private String email;
        @Mask
        private String bankCardNo;
        @Mask
        private String organizationCode;

        public User(String name, int age, String password, String certNo, String mobileNo, String email, String bankCardNo, String organizationCode) {
            this.name = name;
            this.age = age;
            this.password = password;
            this.certNo = certNo;
            this.mobileNo = mobileNo;
            this.email = email;
            this.bankCardNo = bankCardNo;
            this.organizationCode = organizationCode;
        }

        @Override
        public String toString() {
            return ToString.toString(this);
        }
    }
}
