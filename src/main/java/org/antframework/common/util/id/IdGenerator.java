/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2018-01-16 08:39 创建
 */
package org.antframework.common.util.id;

import org.antframework.common.util.file.MapFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * id生成器
 */
public class IdGenerator {
    // 缓存中周期的key
    private static final String CACHE_PERIOD_KEY = "period";
    // 缓存中id的key
    private static final String CACHE_ID_KEY = "id";

    // 每次批量获取的id数量
    private int initAmount;
    // 最大id
    private Long maxId;
    // id锚
    private IdAnchor idAnchor;
    // 批量id
    private Ids ids;

    /**
     * 创建id生成器
     *
     * @param periodType    周期类型
     * @param initAmount    每次批量获取的id数量
     * @param maxId         最大id（不包含。null表示不限制）
     * @param cacheFilePath 缓存文件路径（null表示不使用缓存文件）
     */
    public IdGenerator(PeriodType periodType, int initAmount, Long maxId, String cacheFilePath) {
        if (periodType == null || initAmount <= 0 || (maxId != null && maxId <= 0)) {
            throw new IllegalArgumentException("创建id生成器的参数非法");
        }
        this.initAmount = initAmount;
        this.maxId = maxId;
        // 初始化id锚
        MapFile cacheFile = cacheFilePath == null ? null : new MapFile(cacheFilePath);
        idAnchor = initIdAnchor(periodType, cacheFile);
        // 获取批量id
        ids = idAnchor.next();
    }

    /**
     * 获取id
     */
    public synchronized Id getId() {
        Id id = ids.getId();
        while (id == null) {
            ids = idAnchor.next();
            id = ids.getId();
        }
        return id;
    }

    // 初始化id锚
    private IdAnchor initIdAnchor(PeriodType periodType, MapFile cacheFile) {
        Period period = new Period(periodType, new Date());
        long id = 0;
        if (cacheFile != null) {
            Map<String, String> cache = cacheFile.readAll();
            String periodStr = cache.get(CACHE_PERIOD_KEY);
            if (periodStr != null) {
                period = new Period(periodType, new Date(Long.parseLong(periodStr)));
            }
            String idStr = cache.get(CACHE_ID_KEY);
            if (idStr != null) {
                id = Long.parseLong(idStr);
                if (maxId != null && id >= maxId) {
                    period = period.grow(1);
                    id = 0;
                }
            }
        }

        return new IdAnchor(period, id, cacheFile);
    }

    // id锚
    private class IdAnchor {
        // 周期
        private Period period;
        // id（未被使用）
        private long id;
        // 缓存文件
        private MapFile cacheFile;

        public IdAnchor(Period period, long id, MapFile cacheFile) {
            this.period = period;
            this.id = id;
            this.cacheFile = cacheFile;
        }

        /**
         * 获取下一批id（下一个锚点）
         *
         * @return 批量id
         */
        public Ids next() {
            // 现代化
            modernize();
            // 创建批量id
            long nextId = id + initAmount;
            if (nextId < id) {
                throw new IllegalStateException("运算中超过long类型最大值，无法进行计算");
            }
            if (maxId != null && nextId > maxId) {
                nextId = maxId;
            }
            Ids ids = new Ids(period, id, (int) (nextId - id));
            id = nextId;
            // 抛锚
            drop();

            return ids;
        }

        // 现代化
        private void modernize() {
            Period modernPeriod = new Period(period.getType(), new Date());
            if (period.compareTo(modernPeriod) < 0) {
                period = modernPeriod;
                id = 0;
            }
        }

        // 抛锚
        private void drop() {
            if (maxId != null) {
                period = period.grow((int) (id / maxId));
                id %= maxId;
            }
            if (cacheFile != null) {
                Map<String, String> cache = new HashMap<>();
                if (period.getType() != PeriodType.NONE) {
                    cache.put(CACHE_PERIOD_KEY, Long.toString(period.getDate().getTime()));
                }
                cache.put(CACHE_ID_KEY, Long.toString(id));
                cacheFile.storeAll(cache);
            }
        }
    }

    // 批量id
    private static class Ids {
        // 周期
        private Period period;
        // 开始id（未被使用）
        private long startId;
        // id个数
        private int amount;

        public Ids(Period period, long startId, int amount) {
            this.period = period;
            this.startId = startId;
            this.amount = amount;
        }

        /**
         * 获取id（如果无可用id，则返回null）
         */
        public Id getId() {
            if (getAmount() <= 0) {
                return null;
            }

            Id id = new Id(period, startId);

            startId++;
            amount--;

            return id;
        }

        // 获取id数量（如果id过期，则返回0）
        private int getAmount() {
            Period modernPeriod = new Period(period.getType(), new Date());
            if (period.compareTo(modernPeriod) < 0) {
                return 0;
            }
            return amount;
        }
    }
}
