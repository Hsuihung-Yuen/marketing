package cn.hhy.domain.activity.repository;

import cn.hhy.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.hhy.domain.activity.model.entity.ActivityCountEntity;
import cn.hhy.domain.activity.model.entity.ActivityEntity;
import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;

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

}
