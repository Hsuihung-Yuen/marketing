package cn.hhy.domain.strategy.service;

import cn.hhy.domain.strategy.model.entity.RaffleAwardEntity;
import cn.hhy.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @author Hhy
 * @description 抽奖策略接口
 * @create 2024/7/5
 */
public interface IRaffleStrategy {

    /**
     * 执行抽奖；用抽奖因子入参，执行抽奖计算，返回奖品信息
     *
     * @param raffleFactorEntity 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return 抽奖的奖品
     */
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);

}
