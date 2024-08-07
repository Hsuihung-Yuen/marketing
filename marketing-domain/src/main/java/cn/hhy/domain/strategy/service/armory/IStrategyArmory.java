package cn.hhy.domain.strategy.service.armory;

/**
 * @author Hhy
 * @description 策略装配库(兵工厂)，负责初始化策略计算
 * @create 2024/7/1
 */
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);

    /**
     * 通过活动 id 装配抽奖策略配置
     *
     * @param activityId 活动ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategyByActivityId(Long activityId);


}
