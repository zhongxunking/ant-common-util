/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-20 13:36 创建
 */
package org.antframework.common.util.kit;

import org.junit.Test;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;

/**
 * ip工具类单元测试
 */
public class IPUtilesTests {

    @Test
    public void testGetIPV4() {
        String ipv4 = IPUtils.getIPV4();
    }

    @Test
    public void testGetAllIPV4() {
        List<Inet4Address> inet4Addresses = IPUtils.getAllIPV4();
        List<Inet4Address> inet4Addresses2 = IPUtils.getAllIPV4();
    }

    @Test
    public void testGetIPV6() {
        String ipv6 = IPUtils.getIPV6();
    }

    @Test
    public void testGetAllIPV6() {
        List<Inet6Address> inet6Addresses = IPUtils.getAllIPV6();
        List<Inet6Address> inet6Addresses2 = IPUtils.getAllIPV6();
    }

    @Test
    public void testGetAllInetAddresses() {
        List<InetAddress> inetAddresses = IPUtils.getAllInetAddresses();
        List<InetAddress> inetAddresses2 = IPUtils.getAllInetAddresses();
    }
}
