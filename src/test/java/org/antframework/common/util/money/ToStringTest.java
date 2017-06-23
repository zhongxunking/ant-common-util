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
        User user = new User("zhongxun", 17, "220112198309083907");
        System.out.println(user);

    }

    public static class User {
        private String name;
        private int age;
        @Mask
        private String password;

        public User(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return ToString.toString(this);
        }
    }
}
