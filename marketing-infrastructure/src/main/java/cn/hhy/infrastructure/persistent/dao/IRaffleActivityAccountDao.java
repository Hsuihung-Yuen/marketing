package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.RaffleActivityAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hhy
 * @description 抽奖活动账户表dao
 * @create 2024/7/15
 */
@Mapper
public interface IRaffleActivityAccountDao {

    void insert(RaffleActivityAccount raffleActivityAccount);

    int updateAccountQuota(RaffleActivityAccount raffleActivityAccount);

}