package cn.hhy.domain.strategy.service.rule.tree.impl;

import cn.hhy.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.hhy.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.hhy.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Hhy
 * @description 库存扣减节点
 * @create 2024/7/7
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {

    //先不走数据库查询，先直接接管
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
