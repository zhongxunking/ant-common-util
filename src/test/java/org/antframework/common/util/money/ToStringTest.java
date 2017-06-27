/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-22 22:03 创建
 */
package org.antframework.common.util.money;

import org.antframework.common.util.tostring.ToString;
import org.antframework.common.util.tostring.format.Hide;
import org.antframework.common.util.tostring.format.HideDetail;
import org.antframework.common.util.tostring.format.Mask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ToStringTest {

    @Test
    public void testMask() {
        String[] arr = {"aa", "bb", "cc"};

        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");

        Map<String, Object> map = new HashMap<>();
        map.put("aa", 1);
        map.put("bb", 2);
        map.put("cc", 3);

        User user = new User("zhongxun",
                17,
                "123456",
                "15082119920706049X",
                "18949141125",
                "abcde@163.com",
                "6228480402637874213",
                "91371600MA3DQC9148",
                "asd&$7f23",
                arr,
                arr,
                arr,
                list,
                map);
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
        @Mask
        private String unrecognize;
        private String[] arr0;
        @Hide
        private String[] arr1;
        @HideDetail
        private String[] arr;
        @HideDetail
        private List<String> col;
        @HideDetail
        private Map<String, Object> map;

        public User(String name, int age, String password, String certNo, String mobileNo, String email, String bankCardNo, String organizationCode, String unrecognize, String[] arr0, String[] arr1, String[] arr, List<String> col, Map<String, Object> map) {
            this.name = name;
            this.age = age;
            this.password = password;
            this.certNo = certNo;
            this.mobileNo = mobileNo;
            this.email = email;
            this.bankCardNo = bankCardNo;
            this.organizationCode = organizationCode;
            this.unrecognize = unrecognize;
            this.arr0 = arr0;
            this.arr1 = arr1;
            this.arr = arr;
            this.col = col;
            this.map = map;
        }

        @Override
        public String toString() {
            return ToString.toString(this);
        }
    }
}
