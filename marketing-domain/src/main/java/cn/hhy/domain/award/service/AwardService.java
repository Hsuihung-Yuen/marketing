package cn.hhy.domain.award.service;

import cn.hhy.domain.award.event.SendAwardMessageEvent;
import cn.hhy.domain.award.model.aggregate.UserAwardRecordAggregate;
import cn.hhy.domain.award.model.entity.TaskEntity;
import cn.hhy.domain.award.model.entity.UserAwardRecordEntity;
import cn.hhy.domain.award.model.valobj.TaskStateVO;
import cn.hhy.domain.award.repository.IAwardRepository;
import cn.hhy.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hhy
 * @description 奖品服务
 * @create 2024/7/19
 */
@Service
public class AwardService implements IAwardService{

    @Resource
    private IAwardRepository awardRepository;
    
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;
    
    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {

        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = SendAwardMessageEvent.SendAwardMessage.builder()
                .userId(userAwardRecordEntity.getUserId())
                .awardId(userAwardRecordEntity.getAwardId())
                .awardTitle(userAwardRecordEntity.getAwardTitle())
                .build();

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage
                = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        TaskEntity taskEntity = TaskEntity.builder()
                .userId(userAwardRecordEntity.getUserId())
                .topic(sendAwardMessageEvent.topic())
                .messageId(sendAwardMessageEventMessage.getId())
                .message(sendAwardMessageEventMessage)
                .state(TaskStateVO.create)
                .build();

        // 构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();

        // 存储聚合对象 - 一个事务下，用户的中奖记录
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);

    }
}
