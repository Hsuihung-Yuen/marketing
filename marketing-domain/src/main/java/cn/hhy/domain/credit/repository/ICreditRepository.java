package cn.hhy.domain.credit.repository;

import cn.hhy.domain.credit.model.aggregate.TradeAggregate;
import cn.hhy.domain.credit.model.entity.CreditAccountEntity;

/**
 * @author Hhy
 * @description 用户积分订单仓储
 * @create 2024/7/23
 */
public interface ICreditRepository {

    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

    CreditAccountEntity queryUserCreditAccount(String userId);

}
