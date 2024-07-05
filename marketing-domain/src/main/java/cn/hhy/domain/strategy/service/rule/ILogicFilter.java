package cn.hhy.domain.strategy.service.rule;

import cn.hhy.domain.strategy.model.entity.RuleActionEntity;
import cn.hhy.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author Hhy
 * @description 抽奖规则过滤接口，即为根据规则选择不同的抽奖行为 【直接返回/返回安慰奖/正常抽奖/范围抽奖】
 * @create 2024/7/5
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}

