package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Hhy
 * @description 策略规则 DAO
 * @create 2024/6/29
 */
@Mapper
public interface IStrategyRuleDao {
    List<StrategyRule> queryStrategyRuleList();

    StrategyRule queryStrategyRule(StrategyRule strategyRuleReq);

    String queryStrategyRuleValue(StrategyRule strategyRule);

}
