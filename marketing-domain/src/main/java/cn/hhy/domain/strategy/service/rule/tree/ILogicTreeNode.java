package cn.hhy.domain.strategy.service.rule.tree;

import cn.hhy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author Hhy
 * @description 规则树节点接口
 * @create 2024/7/7
 */
public interface ILogicTreeNode {

    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue);
}
