/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-26 10:37 创建
 */
package org.antframework.common.util.annotation.locate;

import org.antframework.common.util.other.Cache;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;

/**
 * 注解位置定位器
 */
public final class AnnotationLocator {
    // 正在被解析对象的持有器（用于检查循环引用）
    private static final ThreadLocal<Set<Object>> APPENDING_OBJS_HOLDER = new ThreadLocal<>();
    // 附加器
    private static final List<Appender> APPENDERS;

    static {
        APPENDERS = new ArrayList<>();
        APPENDERS.add(new CollectionAppender());
        APPENDERS.add(new ArrayAppender());
        APPENDERS.add(new MapAppender());
        APPENDERS.add(new InnerAppender());
    }

    /**
     * 定位obj对象内的注解（包含被注解标注字段的引用对象）
     *
     * @param obj            对象
     * @param aType          被定位的注解类型
     * @param fieldPredicate 字段断言器（判断是否接受指定字段）
     * @param <A>            被定位的注解
     * @return 位置
     */
    public static <A extends Annotation> List<Position<A>> locate(Object obj, Class<A> aType, Predicate<Field> fieldPredicate) {
        if (APPENDING_OBJS_HOLDER.get() == null) {
            APPENDING_OBJS_HOLDER.set(new HashSet<>());
        }
        List<Position<A>> positions = new LinkedList<>();
        append(positions, obj, aType, fieldPredicate);
        return new ArrayList<>(positions);
    }

    // 将obj内的地址附加到positions
    private static <A extends Annotation> void append(List<Position<A>> positions, Object obj, Class<A> aType, Predicate<Field> fieldPredicate) {
        if (obj == null) {
            return;
        }
        // 循环引用判断
        if (APPENDING_OBJS_HOLDER.get().contains(obj)) {
            return;
        }
        // 执行附加
        APPENDING_OBJS_HOLDER.get().add(obj);
        try {
            Appender<A> appender = chooseAppender(obj);
            appender.append(positions, obj, aType, fieldPredicate);
        } finally {
            APPENDING_OBJS_HOLDER.get().remove(obj);
        }
    }

    // 选择附加器
    private static <A extends Annotation> Appender<A> chooseAppender(Object obj) {
        for (Appender appender : APPENDERS) {
            if (appender.canAppend(obj)) {
                return appender;
            }
        }
        throw new IllegalStateException(String.format("未找到对象[%s]对应的appender", obj));
    }

    // 附加器
    private interface Appender<A extends Annotation> {
        // 能否根据obj执行附加
        boolean canAppend(Object obj);

        // 解析obj包含的位置，并附加到positions
        void append(List<Position<A>> positions, Object obj, Class<A> aType, Predicate<Field> fieldPredicate);
    }

    // 集合附加器
    private static class CollectionAppender<A extends Annotation> implements Appender<A> {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Collection;
        }

        @Override
        public void append(List<Position<A>> positions, Object collection, Class<A> aType, Predicate<Field> fieldPredicate) {
            for (Object obj : (Collection) collection) {
                AnnotationLocator.append(positions, obj, aType, fieldPredicate);
            }
        }
    }

    // 数组附加器
    private static class ArrayAppender<A extends Annotation> implements Appender<A> {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Object[];
        }

        @Override
        public void append(List<Position<A>> positions, Object array, Class<A> aType, Predicate<Field> fieldPredicate) {
            for (Object obj : (Object[]) array) {
                AnnotationLocator.append(positions, obj, aType, fieldPredicate);
            }
        }
    }

    // map的value附加器
    private static class MapAppender<A extends Annotation> implements Appender<A> {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Map;
        }

        @Override
        public void append(List<Position<A>> positions, Object map, Class<A> aType, Predicate<Field> fieldPredicate) {
            for (Object obj : ((Map) map).values()) {
                AnnotationLocator.append(positions, obj, aType, fieldPredicate);
            }
        }
    }

    // 反射解析对象内部属性的附加器
    private static class InnerAppender<A extends Annotation> implements Appender<A> {
        // 执行器缓存
        private final Cache<Class, InnerAppenderExecutor<A>> cache = new Cache<>(InnerAppenderExecutor::new);

        @Override
        public boolean canAppend(Object obj) {
            return true;
        }

        @Override
        public void append(List<Position<A>> positions, Object obj, Class<A> aType, Predicate<Field> fieldPredicate) {
            cache.get(obj.getClass()).execute(positions, obj, aType, fieldPredicate);
        }

        // 执行器
        private static class InnerAppenderExecutor<A extends Annotation> {
            // 注解与对应的字段缓存
            private final Cache<Class<A>, Map<Field, A>> cache = new Cache<>(this::parse);
            // 被反射解析的类型
            private final Class targetClass;

            InnerAppenderExecutor(Class targetClass) {
                this.targetClass = targetClass;
            }

            // 执行
            void execute(List<Position<A>> positions, Object obj, Class<A> aType, Predicate<Field> fieldPredicate) {
                for (Map.Entry<Field, A> entry : cache.get(aType).entrySet()) {
                    if (fieldPredicate.test(entry.getKey())) {
                        positions.add(new Position<>(obj, entry.getKey(), entry.getValue()));
                    }
                    // 递进解析字段值
                    AnnotationLocator.append(positions, ReflectionUtils.getField(entry.getKey(), obj), aType, fieldPredicate);
                }
            }

            // 将被注解标注的字段解析出来
            private Map<Field, A> parse(Class<A> aType) {
                Map<Field, A> fieldAMap = new HashMap<>();
                // 解析每个字段
                ReflectionUtils.doWithFields(targetClass, field -> {
                    A annotation = AnnotatedElementUtils.findMergedAnnotation(field, aType);
                    if (annotation == null) {
                        return;
                    }
                    ReflectionUtils.makeAccessible(field);
                    fieldAMap.put(field, annotation);
                }, field -> !Modifier.isStatic(field.getModifiers()));

                return fieldAMap;
            }
        }
    }

    /**
     * 根据字段类型判断的字段断言器
     */
    public static class TypeFieldPredicate implements Predicate<Field> {
        // 可接受的字段类型
        private final Class[] types;

        /**
         * 构造字段断言器
         *
         * @param types 可接受的字段类型
         */
        public TypeFieldPredicate(Class... types) {
            Assert.notEmpty(types, "可接受的字段类型不能为null，也不能为空");
            this.types = types;
        }

        @Override
        public boolean test(Field field) {
            for (Class<?> type : types) {
                if (type.isAssignableFrom(field.getType())) {
                    return true;
                }
            }
            return false;
        }
    }
}
