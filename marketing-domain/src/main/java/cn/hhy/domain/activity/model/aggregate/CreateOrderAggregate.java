package cn.hhy.domain.activity.model.aggregate;

import cn.hhy.domain.activity.model.entity.ActivityAccountEntity;
import cn.hhy.domain.activity.model.entity.ActivityOrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 下单聚合对象
 * @create 2024/7/16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /**
     * 活动账户实体
     */
    private ActivityAccountEntity activityAccountEntity;

    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;

}
