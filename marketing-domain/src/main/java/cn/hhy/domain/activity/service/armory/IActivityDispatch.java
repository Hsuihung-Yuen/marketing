package cn.hhy.domain.activity.service.armory;

import java.util.Date;

/**
 * @author Hhy
 * @description 活动调度【更新库存】
 * @create 2024/7/17
 */
public interface IActivityDispatch {

    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param sku 互动SKU
     * @param endDateTime 活动结束时间，根据结束时间设置加锁的key为结束时间
     * @return 扣减结果
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);
}
