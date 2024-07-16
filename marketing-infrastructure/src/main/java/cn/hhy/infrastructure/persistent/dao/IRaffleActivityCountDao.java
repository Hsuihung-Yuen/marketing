package cn.hhy.infrastructure.persistent.dao;

import cn.hhy.infrastructure.persistent.po.RaffleActivityCount;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hhy
 * @description 抽奖活动次数配置表Dao
 * @create 2024/7/15
 */
@Mapper
public interface IRaffleActivityCountDao {

    RaffleActivityCount queryRaffleActivityCountByActivityCountId(Long activityCountId);

}
