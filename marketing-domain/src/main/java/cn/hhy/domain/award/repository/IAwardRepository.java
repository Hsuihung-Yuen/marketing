package cn.hhy.domain.award.repository;

import cn.hhy.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Hhy
 * @description 奖品仓储服务
 * @create 2024/7/19
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}
