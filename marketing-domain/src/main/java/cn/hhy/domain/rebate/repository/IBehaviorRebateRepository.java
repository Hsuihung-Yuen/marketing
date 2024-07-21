package cn.hhy.domain.rebate.repository;

import cn.hhy.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.hhy.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.hhy.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Hhy
 * @description 行为返利服务仓储接口
 * @create 2024/7/21
 */
public interface IBehaviorRebateRepository {

    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

}
