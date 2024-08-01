package cn.hhy.domain.activity.service.armory;

import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;
import cn.hhy.domain.activity.repository.IActivityRepository;
import cn.hhy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public boolean assembleActivitySkuByActivityId(Long activityId) {
        //一个活动多个sku行为
        List<ActivitySkuEntity> activitySkuEntities = activityRepository.queryActivitySkuListByActivityId(activityId);
        for (ActivitySkuEntity activitySkuEntity : activitySkuEntities) {
            cacheActivitySkuStockCount(activitySkuEntity.getSku(), activitySkuEntity.getStockCountSurplus());
            // 预热个人活动次数【查询时预热到缓存】
            activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());
        }

        // 预热活动【查询时预热到缓存】
        activityRepository.queryRaffleActivityByActivityId(activityId);

        return true;
    }

    @Override
    public boolean assembleActivitySku(Long sku) {
        // 预热活动sku库存，皆为查询时预热到缓存
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(sku);
        cacheActivitySkuStockCount(sku, activitySkuEntity.getStockCountSurplus());

        // 预热活动次数
        activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 预热活动
        activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());

        return true;
    }

    private void cacheActivitySkuStockCount(Long sku, Integer stockCount) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        activityRepository.cacheActivitySkuStockCount(cacheKey, stockCount);
    }

    @Override
    public boolean subtractionActivitySkuStock(Long sku, Date endDateTime) {
        String cacheKey = Constants.RedisKey.ACTIVITY_SKU_STOCK_COUNT_KEY + sku;
        return activityRepository.subtractionActivitySkuStock(sku, cacheKey, endDateTime);
    }
}
