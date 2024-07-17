package cn.hhy.domain.activity.repository;

import cn.hhy.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.hhy.domain.activity.model.entity.ActivityCountEntity;
import cn.hhy.domain.activity.model.entity.ActivityEntity;
import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;
import cn.hhy.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.util.Date;

/**
 * @author Hhy
 * @description 活动仓储接口
 * @create 2024/7/16
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO activitySkuStockKeyVO);

    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

}
