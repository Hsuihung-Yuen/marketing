package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Hhy
 * @description 抽奖策略 DAO
 * @create 2024/6/29
 */
@Mapper
public interface IStrategyDao {
    List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);

}
