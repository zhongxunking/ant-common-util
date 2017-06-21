/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-12 17:48 创建
 */
package org.antframework.common.util.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * common-util自动配置类
 */
@Configuration
@Import({JpaScanConfiguration.class, ConverterRegistryConfiguration.class})
public class CommonUtilAutoConfiguration {
    // 本配置类的作用就是在spring-boot项目中自动导入相关配置类
}
