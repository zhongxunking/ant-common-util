/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-18 21:20 创建
 */
package org.antframework.common.util.jpa.query.annotation;

import org.antframework.common.util.jpa.query.QueryParam;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 查询参数解析器
 */
public class QueryParamsParser {
    // 缓存（每种类型只会在第一次执行时才会进行解析）
    private static final Map<Class, Set<QueryParamParser>> CACHE = new ConcurrentHashMap<>();

    /**
     * 解析出查询参数
     *
     * @param obj 被解析的对象
     * @return 查询参数
     */
    public static List<QueryParam> parse(Object obj) {
        List<QueryParam> queryParams = new ArrayList<>();
        for (QueryParamParser fieldParser : getFieldParsers(obj.getClass())) {
            queryParams.add(fieldParser.parse(obj));
        }
        return queryParams;
    }

    // 获取字段解析器
    private static Set<QueryParamParser> getFieldParsers(Class clazz) {
        Set<QueryParamParser> fieldParsers = CACHE.get(clazz);
        if (fieldParsers == null) {
            synchronized (CACHE) {
                fieldParsers = CACHE.get(clazz);
                if (fieldParsers == null) {
                    fieldParsers = parseToFieldParser(clazz);
                    CACHE.put(clazz, fieldParsers);
                }
            }
        }
        return fieldParsers;
    }

    // 解析出字段解析器
    private static Set<QueryParamParser> parseToFieldParser(Class clazz) {
        Set<QueryParamParser> parsers = new HashSet<>();
        for (Class parsingClass = clazz; parsingClass != null; parsingClass = parsingClass.getSuperclass()) {
            for (Field field : parsingClass.getDeclaredFields()) {
                org.antframework.common.util.jpa.query.annotation.QueryParam queryParamAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, org.antframework.common.util.jpa.query.annotation.QueryParam.class);
                if (queryParamAnnotation == null) {
                    continue;
                }
                QueryParamParser parser = (QueryParamParser) ReflectUtils.newInstance(queryParamAnnotation.parser());
                parser.initialize(field);
                parsers.add(parser);
            }
        }
        return parsers;
    }
}
