package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hhy
 * @description 抽奖活动表Dao
 * @create 2024/7/15
 */
@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}
