package cn.hhy.domain.award.service;

import cn.hhy.domain.award.model.entity.DistributeAwardEntity;
import cn.hhy.domain.award.model.entity.UserAwardRecordEntity;

/**
 * @author Hhy
 * @description 奖品服务接口
 * @create 2024/7/19
 */
public interface IAwardService {

    void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity);

    /**
     * 配送发货奖品到账户
     */
    void distributeAward(DistributeAwardEntity distributeAwardEntity);

}
