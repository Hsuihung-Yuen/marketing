package cn.hhy.domain.activity.service.armory;

import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;
import cn.hhy.domain.activity.repository.IActivityRepository;
import cn.hhy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Hhy
 * @description 活动sku预热
 * @create 2024/7/17
 */
@Slf4j
@Service
public class ActivityArmory implements IActivityArmory,IActivityDispatch{

    @Resource
    private IActivityRepository activityRepository;

    @Override
    public boolean assembleActivitySku(Long sku) {
        // 预热活动sku库存，皆为查询时预热到缓存
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(sku);
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        activityRepository.cacheActivitySkuStockCount(cacheKey, activitySkuEntity.getStockCount());

        // 预热活动
        activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());

        // 预热活动次数
        activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        return true;
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, Date endDateTime) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        return activityRepository.subtractionActivitySkuStock(sku, cacheKey, endDateTime);
    }
}
