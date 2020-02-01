/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-06-17 17:11 创建
 */
package org.antframework.common.util.tostring;

import lombok.AllArgsConstructor;
import org.antframework.common.util.other.Cache;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 将对象转换成字符串工具类
 */
public final class ToString {
    // null字符串
    private static final String NULL_STRING = "null";
    // 正在被解析对象的持有器（用于检查循环引用）
    private static final ThreadLocal<Set<Object>> APPENDING_OBJS_HOLDER = new ThreadLocal<>();
    // 内部附加器（通过反射解析对象内部字段）
    private static final Appender INNER_APPENDER = new InnerAppender();
    // 优先附加器（优先使用本list中的附加器解析对象）
    private static final List<Appender> PRIOR_APPENDERS;

    static {
        PRIOR_APPENDERS = new ArrayList<>();
        PRIOR_APPENDERS.add(new CharSequenceAppender());
        PRIOR_APPENDERS.add(new DateAppender());
        PRIOR_APPENDERS.add(new CollectionAppender());
        PRIOR_APPENDERS.add(new MapAppender());
        PRIOR_APPENDERS.add(new ArrayAppender());
    }

    /**
     * 将对象转换成字符串
     *
     * @param obj 待转换对象
     *            （obj不会被修改。已对循环引用进行检查。
     *            如果obj的类型是数组、集合、Map（包括这些类型的嵌套情况），则obj会被解析成相应的格式，再调用每个元素的toString()；否则会通过反射解析obj内部属性。）
     * @return 转换得到的字符串
     */
    public static String toString(Object obj) {
        if (APPENDING_OBJS_HOLDER.get() == null) {
            APPENDING_OBJS_HOLDER.set(new HashSet<>());
        }

        StringBuilder builder = new StringBuilder();
        appendInner(builder, obj);
        return builder.toString();
    }

    // 附加对象（如果obj是非集合类型、Map、数组，则obj会被通过反射解析内部属性）
    private static void appendInner(StringBuilder builder, Object obj) {
        Appender appender = chooseAppender(obj, true);
        doAppender(appender, builder, obj);
    }

    // 附加对象（如果obj是非集合类型、Map、数组，则会调用obj的toString方法）
    private static void append(StringBuilder builder, Object obj) {
        Appender appender = chooseAppender(obj, false);
        doAppender(appender, builder, obj);
    }

    // 选择适合obj的附加器
    private static Appender chooseAppender(Object obj, boolean inner) {
        if (obj == null) {
            return null;
        }
        for (Appender appender : PRIOR_APPENDERS) {
            if (appender.canAppend(obj)) {
                return appender;
            }
        }
        if (inner) {
            return INNER_APPENDER;
        }
        return null;
    }

    // 执行附加器
    private static void doAppender(Appender appender, StringBuilder builder, Object obj) {
        if (appender == null) {
            if (obj == null) {
                builder.append(NULL_STRING);
            } else {
                builder.append(obj.toString());
            }
            return;
        }

        // 循环引用判断
        if (APPENDING_OBJS_HOLDER.get().contains(obj)) {
            builder.append(ObjectUtils.identityToString(obj));
            return;
        }

        APPENDING_OBJS_HOLDER.get().add(obj);
        try {
            appender.append(builder, obj);
        } finally {
            APPENDING_OBJS_HOLDER.get().remove(obj);
        }
    }

    // 附加器
    private interface Appender {
        // 能否根据obj执行附加
        boolean canAppend(Object obj);

        // 将obj解析成字符串并附加到builder
        void append(StringBuilder builder, Object obj);
    }

    // CharSequence类型附加器（会在字符串两边加双引号，比如：abc会转换成"abc"）
    private static class CharSequenceAppender implements Appender {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof CharSequence;
        }

        @Override
        public void append(StringBuilder builder, Object str) {
            builder.append('"').append((CharSequence) str).append('"');
        }
    }

    // Date类型附加器（会转换成yyyy-MM-dd HH:mm:ss.SSS格式）
    private static class DateAppender implements Appender {
        // 日期转换格式
        private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Date;
        }

        @Override
        public void append(StringBuilder builder, Object date) {
            builder.append(DateFormatUtils.format((Date) date, DATE_FORMAT_PATTERN));
        }
    }

    // 集合类型附加器（会转换成[a,b,c]这种格式，如果有嵌套会进一步解析）
    private static class CollectionAppender implements Appender {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Collection;
        }

        @Override
        public void append(StringBuilder builder, Object collection) {
            builder.append('[');

            for (Object obj : (Collection) collection) {
                ToString.append(builder, obj);
                builder.append(',');
            }
            if (((Collection) collection).size() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            builder.append(']');
        }
    }

    // Map类型附加器（会转换成{key1=value1,key2=value2,key3=value3}这种格式，如果有嵌套会进一步解析）
    private static class MapAppender implements Appender {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Map;
        }

        @Override
        public void append(StringBuilder builder, Object map) {
            builder.append('{');

            Set<Map.Entry> entries = ((Map) map).entrySet();
            for (Map.Entry entry : entries) {
                ToString.append(builder, entry.getKey());
                builder.append('=');
                ToString.append(builder, entry.getValue());
                builder.append(',');
            }
            if (entries.size() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            builder.append('}');
        }
    }

    // 数组附加器（会转换成[a,b,c]这种格式）
    private static class ArrayAppender implements Appender {
        @Override
        public boolean canAppend(Object obj) {
            return obj instanceof Object[];
        }

        @Override
        public void append(StringBuilder builder, Object array) {
            builder.append('[');

            for (Object obj : (Object[]) array) {
                ToString.append(builder, obj);
                builder.append(',');
            }
            if (Array.getLength(array) > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }

            builder.append(']');
        }
    }

    // 反射解析对象内部属性的附加器（会转换成User{name="XXX",age=20}这种格式）
    private static class InnerAppender implements Appender {
        // 执行器缓存（每种类型只会在第一次执行时才会进行解析）
        private static final Cache<Class, InnerAppenderExecutor> EXECUTOR_CACHE = new Cache<>(InnerAppender::parse);

        @Override
        public boolean canAppend(Object obj) {
            return true;
        }

        @Override
        public void append(StringBuilder builder, Object obj) {
            EXECUTOR_CACHE.get(obj.getClass()).execute(builder, obj);
        }

        // 解析（将clazz及其继承的所有非静态属性解析出来，对于指定了formatter的属性则初始化该属性的formatter）
        private static InnerAppenderExecutor parse(Class clazz) {
            List<Field> fields = new ArrayList<>();
            Map<Field, FieldFormatter> formatterMap = new HashMap<>();
            for (Class parsingClass = clazz; parsingClass != null; parsingClass = parsingClass.getSuperclass()) {
                List<Field> parsingFields = new ArrayList<>();
                for (Field field : parsingClass.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    ReflectionUtils.makeAccessible(field);
                    parsingFields.add(field);
                    // 判断该属性是否指定了formatter
                    FieldFormat formatAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, FieldFormat.class);
                    if (formatAnnotation != null) {
                        // 初始化formatter
                        FieldFormatter formatter = (FieldFormatter) ReflectUtils.newInstance(formatAnnotation.formattedBy());
                        formatter.initialize(field);
                        formatterMap.put(field, formatter);
                    }
                }
                // 顺序：按照先父类再子类，每个类中按照属性定义顺序
                fields.addAll(0, parsingFields);
            }

            return new InnerAppenderExecutor(ClassUtils.getShortName(clazz), fields, formatterMap);
        }

        // 执行器
        @AllArgsConstructor
        private static class InnerAppenderExecutor {
            // 类名（简写）
            private final String className;
            // 字段
            private final List<Field> fields;
            // 字段格式化器Map
            private final Map<Field, FieldFormatter> formatterMap;

            // 执行
            void execute(StringBuilder builder, Object obj) {
                builder.append(className).append('{');

                for (Field field : fields) {
                    FieldFormatter formatter = formatterMap.get(field);
                    if (formatter != null) {
                        // 指定了formatter，则按照formatter执行
                        String formattedField = formatter.format(obj);
                        if (formattedField != null) {
                            builder.append(formattedField);
                            builder.append(',');
                        }
                    } else {
                        // 未指定formatter，则按照默认方式执行
                        builder.append(field.getName()).append('=');
                        ToString.append(builder, ReflectionUtils.getField(field, obj));
                        builder.append(',');
                    }
                }
                if (builder.charAt(builder.length() - 1) == ',') {
                    builder.deleteCharAt(builder.length() - 1);
                }

                builder.append('}');
            }
        }
    }
}
