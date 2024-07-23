package cn.hhy.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.hhy.domain.credit.model.aggregate.TradeAggregate;
import cn.hhy.domain.credit.model.entity.CreditAccountEntity;
import cn.hhy.domain.credit.model.entity.CreditOrderEntity;
import cn.hhy.domain.credit.repository.ICreditRepository;
import cn.hhy.infrastructure.persistent.dao.IUserCreditAccountDao;
import cn.hhy.infrastructure.persistent.dao.IUserCreditOrderDao;
import cn.hhy.infrastructure.persistent.po.UserCreditAccount;
import cn.hhy.infrastructure.persistent.po.UserCreditOrder;
import cn.hhy.infrastructure.persistent.redis.IRedisService;
import cn.hhy.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Hhy
 * @description 用户积分仓储
 * @create 2024/7/23
 */
@Slf4j
@Repository
public class CreditRepository implements ICreditRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IUserCreditAccountDao userCreditAccountDao;

    @Resource
    private IUserCreditOrderDao userCreditOrderDao;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();

        // 积分账户
        UserCreditAccount userCreditAccountReq = UserCreditAccount.builder()
                .userId(userId)
                .totalAmount(creditAccountEntity.getAdjustAmount())
                .availableAmount(creditAccountEntity.getAdjustAmount())
                .build();

        // 积分订单
        UserCreditOrder userCreditOrderReq=UserCreditOrder.builder()
                .userId(creditOrderEntity.getUserId())
                .orderId(creditOrderEntity.getOrderId())
                .tradeName(creditOrderEntity.getTradeName().getName())
                .tradeType(creditOrderEntity.getTradeType().getCode())
                .tradeAmount(creditOrderEntity.getTradeAmount())
                .outBusinessNo(creditOrderEntity.getOutBusinessNo())
                .build();

        RLock lock = redisService.getLock(Constants.RedisKey.USER_CREDIT_ACCOUNT_LOCK + userId + Constants.UNDERLINE + creditOrderEntity.getOutBusinessNo());
        try {
            lock.lock(3, TimeUnit.SECONDS);
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {

                    // 1. 保存账户积分
                    UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
                    if (null == userCreditAccount) {
                        userCreditAccountDao.insert(userCreditAccountReq);
                    } else {
                        userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                    }

                    // 2. 保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度异常，唯一索引冲突 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    log.error("调整账户积分额度失败 userId:{} orderId:{}", userId, creditOrderEntity.getOrderId(), e);
                }
                return 1;
            });
        } finally {
            dbRouter.clear();
            lock.unlock();
        }

    }
}
