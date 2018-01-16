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

/**
 *
 */
public class IdGenerator {
    private Long maxId;
    private int initAmount;

    private IdAnchor idAnchor;
    private Ids ids;

    public IdGenerator(PeriodType periodType, int initAmount, Long maxId, String cacheFilePath) {
        this.maxId = maxId;
        this.initAmount = initAmount;

        MapFile cacheFile = new MapFile(cacheFilePath);
        idAnchor = initIdAnchor(periodType, cacheFile);
        ids = idAnchor.next();
    }

    public Id getId() {
        Id id = ids.getId();
        while (id == null) {
            ids = idAnchor.next();
            id = ids.getId();
        }
        return id;
    }

    private IdAnchor initIdAnchor(PeriodType periodType, MapFile cacheFile) {
        Period period = new Period(periodType, new Date());
        if (cacheFile != null) {
            String periodStr = cacheFile.read("period");
            if (periodStr != null) {
                period = new Period(periodType, new Date(Long.parseLong(periodStr)));
            }
        }
        long id = 0;
        if (cacheFile != null) {
            String idStr = cacheFile.read("id");
            if (idStr != null) {
                id = Long.parseLong(idStr);
            }
        }

        return new IdAnchor(period, id, cacheFile);
    }

    private class IdAnchor {
        private Period period;
        private long id;
        private MapFile cacheFile;

        public IdAnchor(Period period, long id, MapFile cacheFile) {
            this.period = period;
            this.id = id;
            this.cacheFile = cacheFile;
        }

        public Ids next() {
            modernize();
            int amount = initAmount;
            if (id + amount > maxId) {
                amount = (int) (maxId - id);
            }
            Ids ids = new Ids(period, id, amount);
            id += amount;
            drop();

            return ids;
        }

        private void modernize() {
            Period modernPeriod = new Period(period.getType(), new Date());
            if (period.compareTo(modernPeriod) < 0) {
                period = modernPeriod;
                id = 0;
            }
        }

        private void drop() {
            period = period.grow((int) (id / maxId));
            id %= maxId;
            if (cacheFile != null) {
                if (period.getType() != PeriodType.NONE) {
                    cacheFile.store("period", Long.toString(period.getDate().getTime()));
                }
                cacheFile.store("id", Long.toString(id));
            }
        }
    }

    private static class Ids {
        // 周期
        private Period period;
        // 开始id（包含）
        private long startId;
        // id个数
        private int amount;

        public Ids(Period period, long startId, int amount) {
            this.period = period;
            this.startId = startId;
            this.amount = amount;
        }

        public Id getId() {
            if (getAmount() <= 0) {
                return null;
            }

            Id id = new Id(period, startId);

            startId++;
            amount--;

            return id;
        }

        private int getAmount() {
            Period modernPeriod = new Period(period.getType(), new Date());
            if (period.compareTo(modernPeriod) < 0) {
                return 0;
            }
            return amount;
        }
    }
}
