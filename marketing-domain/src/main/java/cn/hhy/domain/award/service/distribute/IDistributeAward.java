package cn.hhy.domain.award.service.distribute;

import cn.hhy.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author Hhy
 * @description 分发奖品接口
 * @create 2024/7/23
 */
public interface IDistributeAward {

    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}

