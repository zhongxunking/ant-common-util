/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-20 12:05 创建
 */
package org.antframework.common.util.other;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * ip工具类
 */
public final class IPUtils {
    /**
     * ipv4环回地址
     */
    public static final String IPV4_LOOPBACK = "127.0.0.1";
    /**
     * ipv6环回地址
     */
    public static final String IPV6_LOOPBACK = "::1";
    // 本机所有因特网地址
    private static List<InetAddress> ALL_INET_ADDRESSES;
    // 本机所有ipv4地址
    private static List<Inet4Address> ALL_IPV4;
    // 本机所有ipv6地址
    private static List<Inet6Address> ALL_IPV6;

    /**
     * 获取本机第一个ipv4地址
     */
    public static String getIPV4() {
        for (InetAddress inetAddress : getAllIPV4()) {
            if (!inetAddress.isLoopbackAddress()) {
                return inetAddress.getHostAddress();
            }
        }
        return IPV4_LOOPBACK;
    }

    /**
     * 获取本机所有ipv4地址
     */
    public static List<Inet4Address> getAllIPV4() {
        if (ALL_IPV4 != null) {
            return ALL_IPV4;
        }
        List<Inet4Address> inet4Addresses = new ArrayList<>();
        for (InetAddress inetAddress : getAllInetAddresses()) {
            if (inetAddress instanceof Inet4Address) {
                inet4Addresses.add((Inet4Address) inetAddress);
            }
        }
        return ALL_IPV4 = Collections.unmodifiableList(inet4Addresses);
    }

    /**
     * 获取本机第一个ipv6地址
     */
    public static String getIPV6() {
        for (InetAddress inetAddress : getAllIPV6()) {
            if (!inetAddress.isLoopbackAddress()) {
                return inetAddress.getHostAddress();
            }
        }
        return IPV6_LOOPBACK;
    }

    /**
     * 获取本机所有ipv6地址
     */
    public static List<Inet6Address> getAllIPV6() {
        if (ALL_IPV6 != null) {
            return ALL_IPV6;
        }
        List<Inet6Address> inet6Addresses = new ArrayList<>();
        for (InetAddress inetAddress : getAllInetAddresses()) {
            if (inetAddress instanceof Inet6Address) {
                inet6Addresses.add((Inet6Address) inetAddress);
            }
        }
        return ALL_IPV6 = Collections.unmodifiableList(inet6Addresses);
    }

    /**
     * 获取本机所有的因特网地址
     */
    public static List<InetAddress> getAllInetAddresses() {
        if (ALL_INET_ADDRESSES != null) {
            return ALL_INET_ADDRESSES;
        }
        try {
            List<InetAddress> inetAddresses = new ArrayList<>();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> addresses = networkInterfaces.nextElement().getInetAddresses();
                while (addresses.hasMoreElements()) {
                    inetAddresses.add(addresses.nextElement());
                }
            }
            return ALL_INET_ADDRESSES = Collections.unmodifiableList(inetAddresses);
        } catch (SocketException e) {
            return ExceptionUtils.rethrow(e);
        }
    }
}
