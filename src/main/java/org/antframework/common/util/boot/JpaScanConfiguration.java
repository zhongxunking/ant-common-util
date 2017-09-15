/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-12 17:17 创建
 */
package org.antframework.common.util.boot;

import org.antframework.common.util.jpascanned.JpaScannedPackage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * jpa扫描配置（将本工具相关包加入jpa扫描）
 */
public class JpaScanConfiguration implements BeanFactoryAware, BeanDefinitionRegistryPostProcessor, Ordered {
    // Spring的bean工厂
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<String> packageNames = new ArrayList<>();
        // spring自动配置包
        packageNames.addAll(AutoConfigurationPackages.get(beanFactory));
        // 本工具被jpa扫描的包
        packageNames.add(ClassUtils.getPackageName(JpaScannedPackage.class));

        // 注册需要被jpa扫描的包
        EntityScanPackages.register(registry, packageNames);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public int getOrder() {
        // 防止影响spring其他设置，本配置以最低优先级执行
        return Ordered.LOWEST_PRECEDENCE;
    }
}
