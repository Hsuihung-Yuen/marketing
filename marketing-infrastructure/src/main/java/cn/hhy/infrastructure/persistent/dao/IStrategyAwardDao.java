package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Hhy
 * @description 抽奖策略奖品明细配置 - 概率、规则 DAO
 * @create 2024/6/29
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryStrategyAwardList();
    
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);



}
