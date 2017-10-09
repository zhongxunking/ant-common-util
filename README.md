# 使用文档

## 1. ToString
将类转换为用于打印日志的字符串

    public static class User{
        String name;
        int age;
        @Mask(allMask = true)
        String password;

        // 在此省略getter、setter

        @Override
        public String toString() {
            return ToString.toString(this);
        }
    }
    
toString得到的字符串类似这样：

    User{name="张三",age=20,password=******}
    
除了@Mask（掩码）还有@Hide（隐藏）、@HideDetail（隐藏细节）注解，详细看对应类上的注释。

ToString提供良好的扩展功能，请查看FieldFormatter、@FieldFormat。前面介绍的@Mask、@Hide、@HideDetail都是根据此扩展实现的，你自己需要扩展时可以参考它们。

## 2. Money
钱。提供元到分、字符串的转换。虽然可以使用long来表示以分为单位的钱，但是从前后端交互、日志等用long来表示还是多有不便，为此专门编写了Money类。

#### 2.1 基本介绍

        // new入参的单位是分
        Money money1 = new Money(10012);
        // Money.toString()得到的是以元为单位的字符串，比如money1.toString()得到的是"100.12"
        
        // amount()传入的是以元为单位的字符串
        Money money2 = Money.amount("100.12");

Money提供jsr303校验注解@MoneyConstraint：

        public class TradeOrder {
            // 最少1元
            @MoneyConstraint(min = 100)
            private Money amount;
            
            // 省略getter、setter
        }

#### 2.2 转换器
用long来表示钱的话，前后端交互时可能需要将以元为单位的字符串转换为以分为单位的long，比较繁琐。

Money提供转换器将前端传进来的以元为单位的字符串自动转换为Money类（前提是使用的springMvc），具体配置如下：
1. 如果是spring-boot项目则不需要进行任何配置。

    如果是非spring-boot项目则需要手动引入配置类ConverterRegistryConfiguration，比如：

        @Configuration
        @Import(ConverterRegistryConfiguration.class)
        public class MyConfiguration {
        }

2. 在Controller入参中可以直接使用Money，不需要自己转换：


        @Controller
        public class TradeController {
            @RequestMapping("/trade")
            public String findApp(@RequestParam Money amount) {
                // 执行具体业务
            }
        }

#### 2.3 在jpa中使用Money
在编写jpa实体时可以直接使用Money类，Money类型字段在数据库表中的类型与Long在数据库表中的类型相同。
1. 如果是在spring-boot项目中，使用方式和其他普通类型没有任何区别，如下：


        @Entity
        public class Trade  {
            @Column
            private Money amount;
            // 在此省略其他字段
        }

2. 如果实在非spring-boot项目中，使用时需关联转换器，如下：


        @Entity
        public class Trade {
            @Column
            @Convert(converter = MoneyJpaConverter.class)  // 关联转换器
            private Money amount;
            // 在此省略其他字段
        }

## 3. 服务调用契约
一个公司内往往有多个系统，各系统之间会进行相互调用。如果服务调用中没有统一的契约，则可能存在潜在风险，维护成本也高。为此本工具包提供了一套服务调用的契约，包含抽象order、抽象result、jsr303校验，分页查询的order和result。

#### 3.1 抽象order（AbstractOrder）
AbstractOrder是一个抽象类，它提供了触发jsr303校验的check()方法，使用时自己的Order需继承它。比如：

        public class TradeOrder extends AbstractOrder {
            // 付款人账号
            @NotBlank
            private String payerNo;
            // 收款人账号
            @NotBlank
            private String payeeNo;
            // 金额（最少1元）
            @MoneyConstraint(min = 100)
            private Money amount;
        
            // 省略getter、setter
        }

触发jsr303校验：

        // 校验
        tradeOrder.check();
        // 需进行分组校验，则调用下面方法（XxxGroup是你定义的分组）
        tradeOrder.checkWithGroups(XxxGroup.clss);

#### 3.2 抽象result（AbstractResult）
AbstractResult是一个抽象类，它提供status（状态）、code（结果码）、message（描述）三个字段，使用时自己的result需继承它，比如：

        public class TradeResult extends AbstractResult {
            // 交易流水
            private String tradeNo;
            
            // 省略getter、setter
        }

#### 3.3 服务接口定义
服务接口定义遵照以下方式：入参只有一个order，返回结果是result。类似这样：

        public interface TradeService {
            // 交易
            TradeResult trade(TradeOrder order);
            
            // 省略其他接口
        }

#### 3.3 分页
考虑到分页查询场景比较常用，所以提供了分页查询的Oder和Result抽象类：AbstractQueryOrder、AbstractQueryResult

## 4. 常用校验
本工具包提供身份证号、手机号、邮箱校验工具类，使用时可以到包路径org.antframework.common.util.validation.validator下查找。

## 5. zookeeper工具类
ZkTemplate是简单操作zookeeper的工具类，提供创建节点及其父节点、删除节点及其子节点、对节点设置监听器等方法。

        // 初始化zookeeper客户端
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .namespace("ant-common-util/test")
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        zkClient.start();
        
        // 创建ZkTemplate
        ZkTemplate zkTemplate = new ZkTemplate(zkClient);
        
        // 创建节点及其父节点
        zkTemplate.createNode("/dev/aa");
        // 删除节点及其子节点
        zkTemplate.deleteNode("/dev");
        // 监听节点（xxxListener是你自己定义的监听器）
        zkTemplate.listenNode("/dev/aa", false, xxxListener);

