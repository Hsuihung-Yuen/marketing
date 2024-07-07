package cn.hhy.domain.strategy.service.rule.tree.impl;

import cn.hhy.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.hhy.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.hhy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Hhy
 * @description 次数锁节点
 * @create 2024/7/7
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {

    //先通过，后续在加逻辑，其他树节点也一样
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
