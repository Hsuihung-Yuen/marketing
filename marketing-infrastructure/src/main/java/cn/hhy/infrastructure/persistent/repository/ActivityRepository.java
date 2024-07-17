package cn.hhy.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.hhy.domain.activity.model.aggregate.CreateOrderAggregate;
import cn.hhy.domain.activity.model.entity.ActivityCountEntity;
import cn.hhy.domain.activity.model.entity.ActivityEntity;
import cn.hhy.domain.activity.model.entity.ActivityOrderEntity;
import cn.hhy.domain.activity.model.entity.ActivitySkuEntity;
import cn.hhy.domain.activity.model.valobj.ActivityStateVO;
import cn.hhy.domain.activity.repository.IActivityRepository;
import cn.hhy.infrastructure.persistent.dao.*;
import cn.hhy.infrastructure.persistent.po.*;
import cn.hhy.infrastructure.persistent.redis.IRedisService;
import cn.hhy.types.common.Constants;
import cn.hhy.types.enums.ResponseCode;
import cn.hhy.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @author Hhy
 * @description
 * @create 2024/7/16
 */
@Slf4j
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IRaffleActivityDao raffleActivityDao;

    @Resource
    private IRaffleActivitySkuDao raffleActivitySkuDao;

    @Resource
    private IRaffleActivityCountDao raffleActivityCountDao;

    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;

    @Resource
    private IRaffleActivityAccountDao raffleActivityAccountDao;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IDBRouterStrategy dbRouter;


    @Override
    public ActivitySkuEntity queryActivitySku(Long sku) {
        RaffleActivitySku raffleActivitySku = raffleActivitySkuDao.queryActivitySku(sku);
        return ActivitySkuEntity.builder()
                .sku(raffleActivitySku.getSku())
                .activityId(raffleActivitySku.getActivityId())
                .activityCountId(raffleActivitySku.getActivityCountId())
                .stockCount(raffleActivitySku.getStockCount())
                .stockCountSurplus(raffleActivitySku.getStockCountSurplus())
                .build();
    }

    @Override
    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_KEY + activityId;
        ActivityEntity activityEntity = redisService.getValue(cacheKey);
        if (null != activityEntity) return activityEntity;
        // 从库中获取数据
        RaffleActivity raffleActivity = raffleActivityDao.queryRaffleActivityByActivityId(activityId);
        activityEntity = ActivityEntity.builder()
                .activityId(raffleActivity.getActivityId())
                .activityName(raffleActivity.getActivityName())
                .activityDesc(raffleActivity.getActivityDesc())
                .beginDateTime(raffleActivity.getBeginDateTime())
                .endDateTime(raffleActivity.getEndDateTime())
                .strategyId(raffleActivity.getStrategyId())
                .state(ActivityStateVO.valueOf(raffleActivity.getState()))
                .build();
        redisService.setValue(cacheKey, activityEntity);
        return activityEntity;
    }

    @Override
    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.ACTIVITY_COUNT_KEY + activityCountId;
        ActivityCountEntity activityCountEntity = redisService.getValue(cacheKey);
        if (null != activityCountEntity) return activityCountEntity;
        // 从库中获取数据
        RaffleActivityCount raffleActivityCount = raffleActivityCountDao.queryRaffleActivityCountByActivityCountId(activityCountId);
        activityCountEntity = ActivityCountEntity.builder()
                .activityCountId(raffleActivityCount.getActivityCountId())
                .totalCount(raffleActivityCount.getTotalCount())
                .dayCount(raffleActivityCount.getDayCount())
                .monthCount(raffleActivityCount.getMonthCount())
                .build();
        redisService.setValue(cacheKey, activityCountEntity);
        return activityCountEntity;
    }

    @Override
    public void doSaveOrder(CreateOrderAggregate createOrderAggregate) {

        try {
            // 订单对象
            ActivityOrderEntity activityOrderEntity=createOrderAggregate.getActivityOrderEntity();
            RaffleActivityOrder raffleActivityOrder=RaffleActivityOrder.builder()
                    .userId(activityOrderEntity.getUserId())
                    .sku(activityOrderEntity.getSku())
                    .activityId(activityOrderEntity.getActivityId())
                    .activityName(activityOrderEntity.getActivityName())
                    .strategyId(activityOrderEntity.getStrategyId())
                    .orderId(activityOrderEntity.getOrderId())
                    .orderTime(activityOrderEntity.getOrderTime())
                    .totalCount(createOrderAggregate.getTotalCount())
                    .dayCount(createOrderAggregate.getDayCount())
                    .monthCount(createOrderAggregate.getMonthCount())
                    .state(activityOrderEntity.getState().getCode())
                    .outBusinessNo(activityOrderEntity.getOutBusinessNo())
                    .build();

            // 账户对象
            RaffleActivityAccount raffleActivityAccount =RaffleActivityAccount.builder()
                    .userId(createOrderAggregate.getUserId())
                    .activityId(createOrderAggregate.getActivityId())
                    .totalCount(createOrderAggregate.getTotalCount())
                    .totalCountSurplus(createOrderAggregate.getTotalCount())
                    .dayCount(createOrderAggregate.getDayCount())
                    .dayCountSurplus(createOrderAggregate.getDayCount())
                    .monthCount(createOrderAggregate.getMonthCount())
                    .monthCountSurplus(createOrderAggregate.getMonthCount())
                    .build();

            // 以用户ID作为切分键，通过 doRouter 设定路由【这样就保证了下面的操作，都是同一个连接下，也就保证了事务的特性】
            dbRouter.doRouter(createOrderAggregate.getUserId());

            // 编程式事务
            transactionTemplate.execute(status -> {
                try {
                    // 1. 写入订单
                    raffleActivityOrderDao.insert(raffleActivityOrder);
                    // 2. 更新账户
                    int count = raffleActivityAccountDao.updateAccountQuota(raffleActivityAccount);
                    // 3. 创建账户 - 更新为0，则账户不存在，创新新账户。
                    if (0 == count) {
                        raffleActivityAccountDao.insert(raffleActivityAccount);
                    }
                    //这里返回前可以将账单id添加到布隆过滤器，防止因为其他原因导致无用的重试
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入订单记录，唯一索引冲突 userId: {} activityId: {} sku: {}", activityOrderEntity.getUserId(), activityOrderEntity.getActivityId(), activityOrderEntity.getSku(), e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode());
                }
            });

        } finally {
            dbRouter.clear();
        }

    }
}
