/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-23 17:36 创建
 */
package org.antframework.common.util.tostring.format;

import org.junit.Test;

/**
 *
 */
public class MaskUtilTest {

    @Test
    public void testMask(){
        String str="15922809178";
        System.out.println(MaskUtils.mask(str,0,120));
    }

}
