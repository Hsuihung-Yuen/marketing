package cn.hhy.domain.activity.service.quota.policy.impl;

import cn.hhy.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import cn.hhy.domain.activity.model.valobj.OrderStateVO;
import cn.hhy.domain.activity.repository.IActivityRepository;
import cn.hhy.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Service;

/**
 * @author Hhy
 * @description 积分兑换，支付类订单
 * @create 2024/7/24
 */
@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {

    private final IActivityRepository activityRepository;

    public CreditPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditPayOrder(createQuotaOrderAggregate);
    }
}
