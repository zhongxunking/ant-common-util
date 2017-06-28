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

import java.util.*;

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

        User user = new User(0, 1, 100,
                "zhongxun",
                17,
                new Date(),
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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String str = ToString.toString(user);
            System.out.println(str);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static class Base {
        private long id0;
        private long id1;
        private long amount;

        public Base(long id0, long id1, long amount) {
            this.id0 = id0;
            this.id1 = id1;
            this.amount = amount;
        }
    }

    public static class User extends Base {
        private String name;
        private int age;
        private Date birthDay;
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

        public User(long id0, long id1, long amount, String name, int age, Date birthDay, String password, String certNo, String mobileNo, String email, String bankCardNo, String organizationCode, String unrecognize, String[] arr0, String[] arr1, String[] arr, List<String> col, Map<String, Object> map) {
            super(id0, id1, amount);
            this.name = name;
            this.age = age;
            this.birthDay = birthDay;
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
    }
}
