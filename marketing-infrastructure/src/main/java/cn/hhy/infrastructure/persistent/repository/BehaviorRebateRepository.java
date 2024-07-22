package cn.hhy.infrastructure.persistent.repository;

import cn.bugstack.middleware.db.router.strategy.IDBRouterStrategy;
import cn.hhy.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import cn.hhy.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import cn.hhy.domain.rebate.model.entity.TaskEntity;
import cn.hhy.domain.rebate.model.valobj.BehaviorTypeVO;
import cn.hhy.domain.rebate.model.valobj.DailyBehaviorRebateVO;
import cn.hhy.domain.rebate.repository.IBehaviorRebateRepository;
import cn.hhy.infrastructure.persistent.dao.IDailyBehaviorRebateDao;
import cn.hhy.infrastructure.persistent.dao.ITaskDao;
import cn.hhy.infrastructure.persistent.dao.IUserBehaviorRebateOrderDao;
import cn.hhy.infrastructure.persistent.event.EventPublisher;
import cn.hhy.infrastructure.persistent.po.DailyBehaviorRebate;
import cn.hhy.infrastructure.persistent.po.Task;
import cn.hhy.infrastructure.persistent.po.UserBehaviorRebateOrder;
import cn.hhy.types.enums.ResponseCode;
import cn.hhy.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hhy
 * @description 行为返利服务仓储实现
 * @create 2024/7/21
 */
@Slf4j
@Repository
public class BehaviorRebateRepository implements IBehaviorRebateRepository {

    @Resource
    private IDailyBehaviorRebateDao dailyBehaviorRebateDao;

    @Resource
    private IUserBehaviorRebateOrderDao userBehaviorRebateOrderDao;

    @Resource
    private ITaskDao taskDao;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private EventPublisher eventPublisher;

    @Override
    public List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO) {
        List<DailyBehaviorRebate> dailyBehaviorRebates = dailyBehaviorRebateDao.queryDailyBehaviorRebateByBehaviorType(behaviorTypeVO.getCode());
        List<DailyBehaviorRebateVO> dailyBehaviorRebateVOS = new ArrayList<>(dailyBehaviorRebates.size());
        for (DailyBehaviorRebate dailyBehaviorRebate : dailyBehaviorRebates) {
            dailyBehaviorRebateVOS.add(
                    DailyBehaviorRebateVO.builder()
                            .behaviorType(dailyBehaviorRebate.getBehaviorType())
                            .rebateDesc(dailyBehaviorRebate.getRebateDesc())
                            .rebateType(dailyBehaviorRebate.getRebateType())
                            .rebateConfig(dailyBehaviorRebate.getRebateConfig())
                            .build()
            );
        }
        return dailyBehaviorRebateVOS;
    }

    @Override
    public void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates) {
        try {
            dbRouter.doRouter(userId);
            transactionTemplate.execute(status -> {
                try {
                    for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
                        BehaviorRebateOrderEntity behaviorRebateOrderEntity = behaviorRebateAggregate.getBehaviorRebateOrderEntity();
                        // 用户行为返利订单对象
                        UserBehaviorRebateOrder userBehaviorRebateOrder =UserBehaviorRebateOrder.builder()
                                .userId(behaviorRebateOrderEntity.getUserId())
                                .orderId(behaviorRebateOrderEntity.getOrderId())
                                .behaviorType(behaviorRebateOrderEntity.getBehaviorType())
                                .rebateDesc(behaviorRebateOrderEntity.getRebateDesc())
                                .rebateType(behaviorRebateOrderEntity.getRebateType())
                                .rebateConfig(behaviorRebateOrderEntity.getRebateConfig())
                                .outBusinessNo(behaviorRebateOrderEntity.getOutBusinessNo())
                                .bizId(behaviorRebateOrderEntity.getBizId())
                                .build();
                        userBehaviorRebateOrderDao.insert(userBehaviorRebateOrder);

                        // 任务对象
                        TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
                        Task task = Task.builder()
                                .userId(taskEntity.getUserId())
                                .topic(taskEntity.getTopic())
                                .messageId(taskEntity.getMessageId())
                                .message(JSON.toJSONString(taskEntity.getMessage()))
                                .state(taskEntity.getState().getCode())
                                .build();
                        taskDao.insert(task);
                    }
                    return 1;
                } catch (DuplicateKeyException e) {
                    status.setRollbackOnly();
                    log.error("写入返利记录，唯一索引冲突 userId: {}", userId, e);
                    throw new AppException(ResponseCode.INDEX_DUP.getCode(), ResponseCode.INDEX_DUP.getInfo());
                }
            });
        } finally {
            dbRouter.clear();
        }

        // 同步发送MQ消息
        for (BehaviorRebateAggregate behaviorRebateAggregate : behaviorRebateAggregates) {
            TaskEntity taskEntity = behaviorRebateAggregate.getTaskEntity();
            Task task = new Task();
            task.setUserId(taskEntity.getUserId());
            task.setMessageId(taskEntity.getMessageId());

            try {
                // 发送消息【在事务外执行，如果失败还有任务补偿】
                eventPublisher.publish(taskEntity.getTopic(), taskEntity.getMessage());
                // 更新数据库记录，task 任务表
                taskDao.updateTaskSendMessageCompleted(task);
            } catch (Exception e) {
                log.error("写入返利记录，发送MQ消息失败 userId: {} topic: {}", userId, task.getTopic());
                taskDao.updateTaskSendMessageFail(task);
            }
        }
    }

    @Override
    public List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo) {
        // 1. 请求对象
        UserBehaviorRebateOrder userBehaviorRebateOrderReq = UserBehaviorRebateOrder.builder()
                .userId(userId)
                .outBusinessNo(outBusinessNo)
                .build();
        // 2. 查询结果
        List<UserBehaviorRebateOrder> userBehaviorRebateOrderResList = userBehaviorRebateOrderDao.queryOrderByOutBusinessNo(userBehaviorRebateOrderReq);
        List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = new ArrayList<>(userBehaviorRebateOrderResList.size());
        for (UserBehaviorRebateOrder userBehaviorRebateOrder : userBehaviorRebateOrderResList) {
            BehaviorRebateOrderEntity behaviorRebateOrderEntity = BehaviorRebateOrderEntity.builder()
                    .userId(userBehaviorRebateOrder.getUserId())
                    .orderId(userBehaviorRebateOrder.getOrderId())
                    .behaviorType(userBehaviorRebateOrder.getBehaviorType())
                    .rebateDesc(userBehaviorRebateOrder.getRebateDesc())
                    .rebateType(userBehaviorRebateOrder.getRebateType())
                    .rebateConfig(userBehaviorRebateOrder.getRebateConfig())
                    .outBusinessNo(userBehaviorRebateOrder.getOutBusinessNo())
                    .bizId(userBehaviorRebateOrder.getBizId())
                    .build();
            behaviorRebateOrderEntities.add(behaviorRebateOrderEntity);
        }
        return behaviorRebateOrderEntities;
    }
}
