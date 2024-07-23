package cn.hhy.domain.award.repository;

import cn.hhy.domain.award.model.aggregate.GiveOutPrizesAggregate;
import cn.hhy.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Hhy
 * @description 奖品仓储服务
 * @create 2024/7/19
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    String queryAwardConfig(Integer awardId);

    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);

    String queryAwardKey(Integer awardId);

}
