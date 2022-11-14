/*
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-26 16:20 创建
 */
package org.antframework.common.util.annotation.locate;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

/**
 *
 */
public class AnnotationLocatorTest {

    @Test
    public void testLocate() {
        Product product = new Product();

        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Goods goods = new Goods();
            goods.setGoodsId("100" + i);
//            goods.setName("开心果" + i);
            goods.setDescription("口感香脆" + i);
            goods.setObj(product);

            goodsList.add(goods);
        }

        product.setProductId("001");
        product.setName("过年送礼包");
        product.setDescription("包含很多小零食");
        product.setAmount(1000);
        product.setGoodsList(goodsList);
        product.setGoodses(goodsList.toArray(new Goods[0]));

        AnnotationLocator.locate(product, Tag.class, new AnnotationLocator.TypeFieldPredicate(String.class));
        long startTime = System.currentTimeMillis();
        int count = 1000000;
        for (int i = 0; i < count; i++) {
            List<AnnotationLocator.Position<Tag>> positions = AnnotationLocator.locate(product, Tag.class, new AnnotationLocator.TypeFieldPredicate(String.class));
            Assert.assertEquals(10, positions.size());
        }
        long timeCost = System.currentTimeMillis() - startTime;
        System.out.println("AnnotationLocator性能：");
        System.out.println(String.format("循环次数：%s，总耗时：%d毫秒，平均耗时：%d纳秒，tps：%d", count, timeCost, timeCost * 1000L * 1000 / count, count * 1000L / timeCost));
    }


    @Documented
    @Target({FIELD, ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Tag {
    }

    @Getter
    @Setter
    private static class Product {
        private String productId;
        @Tag
        public static String myStaticName;
        @Tag
        private String name;
        @Tag
        private String description;
        @Tag
        private int amount;
        @Tag
        private List<Goods> goodsList;
        @Tag
        private Goods[] goodses;
    }

    @Getter
    @Setter
    private static class Goods {
        private String goodsId;
        @Tag
        private String name;
        @Tag
        private String description;
        @Tag
        private Object obj;
    }
}
