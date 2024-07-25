package cn.hhy.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.hhy.domain.award.model.valobj.AccountStatusVO;
import cn.hhy.domain.credit.model.aggregate.TradeAggregate;
import cn.hhy.domain.credit.model.entity.CreditAccountEntity;
import cn.hhy.domain.credit.model.entity.CreditOrderEntity;
import cn.hhy.domain.credit.model.entity.TaskEntity;
import cn.hhy.domain.credit.repository.ICreditRepository;
import cn.hhy.infrastructure.persistent.dao.ITaskDao;
import cn.hhy.infrastructure.persistent.dao.IUserCreditAccountDao;
import cn.hhy.infrastructure.persistent.dao.IUserCreditOrderDao;
import cn.hhy.infrastructure.persistent.event.EventPublisher;
import cn.hhy.infrastructure.persistent.po.Task;
import cn.hhy.infrastructure.persistent.po.UserCreditAccount;
import cn.hhy.infrastructure.persistent.po.UserCreditOrder;
import cn.hhy.infrastructure.persistent.redis.IRedisService;
import cn.hhy.types.common.Constants;
import cn.hhy.types.enums.ResponseCode;
import cn.hhy.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    private ITaskDao taskDao;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private EventPublisher eventPublisher;

    @Override
    public void saveUserCreditTradeOrder(TradeAggregate tradeAggregate) {
        String userId = tradeAggregate.getUserId();
        CreditAccountEntity creditAccountEntity = tradeAggregate.getCreditAccountEntity();
        CreditOrderEntity creditOrderEntity = tradeAggregate.getCreditOrderEntity();
        TaskEntity taskEntity = tradeAggregate.getTaskEntity();

        // 积分账户
        UserCreditAccount userCreditAccountReq = UserCreditAccount.builder()
                .userId(userId)
                .totalAmount(creditAccountEntity.getAdjustAmount())
                .availableAmount(creditAccountEntity.getAdjustAmount())
                .accountStatus(AccountStatusVO.open.getCode())
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

        Task task=Task.builder()
                .userId(taskEntity.getUserId())
                .topic(taskEntity.getTopic())
                .messageId(taskEntity.getMessageId())
                .message(JSON.toJSONString(taskEntity.getMessage()))
                .state(taskEntity.getState().getCode())
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
                        BigDecimal availableAmount = userCreditAccountReq.getAvailableAmount();
                        if (availableAmount.compareTo(BigDecimal.ZERO) >= 0) {
                            userCreditAccountDao.updateAddAmount(userCreditAccountReq);
                        } else {
                            int subtractionCount = userCreditAccountDao.updateSubtractionAmount(userCreditAccountReq);
                            if (1 != subtractionCount) {
                                status.setRollbackOnly();
                                throw new AppException(ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getCode(), ResponseCode.USER_CREDIT_ACCOUNT_NO_AVAILABLE_AMOUNT.getInfo());
                            }
                        }
                    }

                    // 2. 保存账户订单
                    userCreditOrderDao.insert(userCreditOrderReq);

                    // 3. 写入任务
                    taskDao.insert(task);
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
            if (lock.isLocked()) {
                lock.unlock();
            }
        }

        try {
            // 发送消息【在事务外执行，如果失败还有任务补偿】
            eventPublisher.publish(task.getTopic(), task.getMessage());

            // 更新数据库记录，task 任务表
            taskDao.updateTaskSendMessageCompleted(task);
            log.info("调整账户积分记录，发送MQ消息完成 userId: {} orderId:{} topic: {}", userId, creditOrderEntity.getOrderId(), task.getTopic());
        } catch (Exception e) {
            log.error("调整账户积分记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
            taskDao.updateTaskSendMessageFail(task);
        }
    }

    @Override
    public CreditAccountEntity queryUserCreditAccount(String userId) {
        UserCreditAccount userCreditAccountReq = UserCreditAccount.builder().userId(userId).build();
        try {
            dbRouter.doRouter(userId);
            UserCreditAccount userCreditAccount = userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
            BigDecimal availableAmount = BigDecimal.ZERO;
            if (null != userCreditAccount) {
                availableAmount = userCreditAccount.getAvailableAmount();
            }
            return CreditAccountEntity.builder()
                    .userId(userId)
                    .adjustAmount(availableAmount)
                    .build();
        } finally {
            dbRouter.clear();
        }

    }

}
