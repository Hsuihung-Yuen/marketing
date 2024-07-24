package cn.hhy.domain.activity.service.quota.policy;

import cn.hhy.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author Hhy
 * @description 交易策略接口，包括：直接返利sku（不用支付）、通过积分兑换（需要支付）
 * @create 2024/7/24
 */
public interface ITradePolicy {

    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}
