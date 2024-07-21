package cn.hhy.domain.rebate.service;

import cn.hhy.domain.rebate.model.entity.BehaviorEntity;

import java.util.List;

/**
 * @author Hhy
 * @description 行为返利服务接口
 * @create 2024/7/21
 */
public interface IBehaviorRebateService {

    /**
     * 创建行为动作的入账订单
     *
     * @param behaviorEntity 行为实体对象
     * @return 订单ID（每个行为多个返利，每个返利都有一个id）
     */
    List<String> createOrder(BehaviorEntity behaviorEntity);

}
