package cn.hhy.domain.strategy.service.rule.chain;

/**
 * @author Hhy
 * @description 责任链装配
 * @create 2024/7/6
 */
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);
}
