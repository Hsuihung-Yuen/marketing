package cn.hhy.domain.strategy.service.armory;

/**
 * @author Hhy
 * @description 策略抽奖调度
 * @create 2024/7/4
 */
public interface IStrategyDispatch {

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    Integer getRandomAwardId(String key);
}
