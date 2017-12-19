/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-12-19 10:06 创建
 */
package org.antframework.common.util.jpa.query.annotation;

import org.antframework.common.util.jpa.query.QueryParam;
import org.antframework.common.util.jpa.query.annotation.operator.*;
import org.junit.Test;

import java.util.List;

/**
 * QueryParamsParser单元测试
 */
public class QueryParamsParserTest {

    @Test
    public void testParse() {
        QueryUserOrder order = new QueryUserOrder();
        order.setSex("man");
        order.setNotColor("white");
        order.setStartGrade(50);
        order.setEndGrade(200);
        order.setStartAge(10);
        order.setEndAge(30);
        order.setName("zhongxun");
        order.setLname("zhongxun");
        order.setRname("zhongxun");
        order.setLrname("zhongxun");
        order.setEat("orange");
        order.setDrink("coffee");
        order.setFrends(new String[]{"张三", "李四"});

        List<QueryParam> queryParams = QueryParamsParser.parse(order);
    }

    public static class QueryUserOrder {
        @QueryEQ
        private String sex;

        @QueryNotEQ(attrName = "color")
        private String notColor;

        @QueryGT(attrName = "grade")
        private int startGrade;

        @QueryLT(attrName = "grade")
        private int endGrade;

        @QueryGTE(attrName = "age")
        private int startAge;

        @QueryLTE(attrName = "age")
        private int endAge;

        @QueryLike(leftLike = false, rightLike = false)
        private String name;

        @QueryLike(attrName = "name", rightLike = false)
        private String lname;

        @QueryLike(attrName = "name", leftLike = false)
        private String rname;

        @QueryLike(attrName = "name")
        private String lrname;

        @QueryNull
        private String eat;

        @QueryNotNull
        private String drink;

        @QueryIn
        private String[] frends;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNotColor() {
            return notColor;
        }

        public void setNotColor(String notColor) {
            this.notColor = notColor;
        }

        public int getStartAge() {
            return startAge;
        }

        public void setStartAge(int startAge) {
            this.startAge = startAge;
        }

        public int getEndAge() {
            return endAge;
        }

        public void setEndAge(int endAge) {
            this.endAge = endAge;
        }

        public int getStartGrade() {
            return startGrade;
        }

        public void setStartGrade(int startGrade) {
            this.startGrade = startGrade;
        }

        public int getEndGrade() {
            return endGrade;
        }

        public void setEndGrade(int endGrade) {
            this.endGrade = endGrade;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getRname() {
            return rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public String getLrname() {
            return lrname;
        }

        public void setLrname(String lrname) {
            this.lrname = lrname;
        }

        public String getEat() {
            return eat;
        }

        public void setEat(String eat) {
            this.eat = eat;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String[] getFrends() {
            return frends;
        }

        public void setFrends(String[] frends) {
            this.frends = frends;
        }
    }
}
