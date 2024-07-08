package cn.hhy.domain.strategy.service.rule.tree.factory.engine;

import cn.hhy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Hhy
 * @description 规则树组合（引擎）对外接口
 * @create 2024/7/7
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId);
}
