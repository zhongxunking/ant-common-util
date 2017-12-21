/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 21:20 创建
 */
package org.antframework.common.util.query.annotation;

import org.antframework.common.util.other.Cache;
import org.antframework.common.util.query.QueryParam;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询参数解析器
 */
public class QueryParamsParser {
    // 缓存（每种类型只会在第一次执行时才会进行解析）
    private static final Cache<Class, List<QueryParamParser>> CACHE = new Cache<>(new Cache.Supplier<Class, List<QueryParamParser>>() {
        @Override
        public List<QueryParamParser> get(Class key) {
            return parseToFieldParser(key);
        }
    });

    /**
     * 解析出查询参数
     *
     * @param obj 被解析的对象
     * @return 查询参数
     */
    public static List<QueryParam> parse(Object obj) {
        List<QueryParam> queryParams = new ArrayList<>();
        for (QueryParamParser fieldParser : CACHE.get(obj.getClass())) {
            QueryParam queryParam = fieldParser.parse(obj);
            if (queryParam != null) {
                queryParams.add(queryParam);
            }
        }
        return queryParams;
    }

    // 解析出字段解析器
    private static List<QueryParamParser> parseToFieldParser(Class clazz) {
        List<QueryParamParser> parsers = new ArrayList<>();
        for (Class parsingClass = clazz; parsingClass != null; parsingClass = parsingClass.getSuperclass()) {
            for (Field field : parsingClass.getDeclaredFields()) {
                // 判断属性是否是查询参数
                org.antframework.common.util.query.annotation.QueryParam queryParamAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, org.antframework.common.util.query.annotation.QueryParam.class);
                if (queryParamAnnotation == null) {
                    continue;
                }
                ReflectionUtils.makeAccessible(field);
                // 创建属性解析器
                QueryParamParser parser = (QueryParamParser) ReflectUtils.newInstance(queryParamAnnotation.parser());
                parser.init(field);
                parsers.add(parser);
            }
        }
        return parsers;
    }
}
