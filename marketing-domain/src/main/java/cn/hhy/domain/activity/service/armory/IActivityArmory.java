package cn.hhy.domain.activity.service.armory;

/**
 * @author Hhy
 * @description 活动预热装配
 * @create 2024/7/17
 */
public interface IActivityArmory {

    boolean assembleActivitySkuByActivityId(Long activityId);

    boolean assembleActivitySku(Long sku);
}

