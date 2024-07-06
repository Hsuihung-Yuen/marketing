package cn.hhy.domain.strategy.service.rule.chain;

/**
 * @author Hhy
 * @description 策略规则责任链接口
 * @create 2024/7/6
 */
public interface ILogicChain extends ILogicChainArmory{

    /**
     * 继承 ILogicChainArmory 可以让调用方只做下面方法的实现，
     * 不需要关心责任链的装配问题
     * @param userId
     * @param strategyId
     * @return 奖品id
     */
    Integer logic(String userId, Long strategyId);
}
