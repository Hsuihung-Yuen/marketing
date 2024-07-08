package cn.hhy.domain.strategy.service.rule.tree.factory;

import cn.hhy.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import cn.hhy.domain.strategy.model.valobj.RuleTreeVO;
import cn.hhy.domain.strategy.service.rule.tree.ILogicTreeNode;
import cn.hhy.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import cn.hhy.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Hhy
 * @description 封装规则树节点，并返回决策引擎
 * @create 2024/7/7
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    /**
     * 决策树动作实体对象
     * 参考当时定义过滤器返回的动作实体
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;

        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }

}
