/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-09-02 16:34 创建
 */
package org.antframework.common.util.facade;

import org.antframework.common.util.other.Cache;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;

/**
 * facade工具类
 */
public class FacadeUtils {
    // 分页查询result的info类型缓存
    private static final Cache<Class, Class> INFO_CLASS_CACHE = new Cache<>(new Cache.Supplier<Class, Class>() {
        @Override
        public Class get(Class key) {
            ResolvableType resolvableType = ResolvableType.forClass(AbstractQueryResult.class, key);
            return resolvableType.getGeneric(0).resolve(Object.class);
        }
    });

    /**
     * 计算总页数
     *
     * @param totalCount 记录总数
     * @param pageSize   每页大小
     */
    public static int calcTotalPage(long totalCount, int pageSize) {
        return (int) ((totalCount + pageSize - 1) / pageSize);
    }

    /**
     * 以默认转换器设置查询result
     *
     * @param result    需被设置的result
     * @param extractor 页面提取器
     * @param <S>       需转换为info的类型
     * @param <T>       info的类型
     */
    public static <S, T extends Serializable> void setQueryResult(AbstractQueryResult<T> result, PageExtractor<S> extractor) {
        setQueryResult(result, extractor, new DefaultConverter<S, T>(INFO_CLASS_CACHE.get(result.getClass())));
    }

    /**
     * 设置查询result
     *
     * @param result    需被设置的result
     * @param extractor 页面提取器
     * @param converter 转换器
     * @param <S>       需转换为info的类型
     * @param <T>       info的类型
     */
    public static <S, T extends Serializable> void setQueryResult(AbstractQueryResult<T> result, PageExtractor<S> extractor, Converter<S, T> converter) {
        result.setTotalCount(extractor.getTotalCount());
        for (S rawInfo : extractor.getRawInfos()) {
            result.addInfo(converter.convert(rawInfo));
        }
    }

    /**
     * 页面提取器
     *
     * @param <S> 需转换为info的类型
     */
    public interface PageExtractor<S> {
        /**
         * 获取记录总数
         */
        long getTotalCount();

        /**
         * 获取当前页未加工的info
         */
        Collection<S> getRawInfos();
    }

    /**
     * spring-data-commons page提取器（spring-data-jpa等可用此提取器）
     *
     * @param <S> 需转换为info的类型
     */
    public static class SpringDataPageExtractor<S> implements PageExtractor<S> {
        // spring-data-commons page
        private Page<S> page;

        public SpringDataPageExtractor(Page<S> page) {
            this.page = page;
        }

        @Override
        public long getTotalCount() {
            return page.getTotalElements();
        }

        @Override
        public Collection<S> getRawInfos() {
            return page.getContent();
        }
    }

    /**
     * 默认转换器
     *
     * @param <S> 源类型
     * @param <T> 目标类型
     */
    public static class DefaultConverter<S, T> implements Converter<S, T> {
        // 目标类型Class
        private Class<T> targetClass;

        public DefaultConverter(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @Override
        public T convert(S source) {
            T target = BeanUtils.instantiate(targetClass);
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }
}
