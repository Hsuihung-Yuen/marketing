package cn.hhy.trigger.listener;

import cn.hhy.domain.award.event.SendAwardMessageEvent;
import cn.hhy.domain.award.model.entity.DistributeAwardEntity;
import cn.hhy.domain.award.service.IAwardService;
import cn.hhy.types.event.BaseEvent;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Hhy
 * @description 用户奖品记录消息消费者
 * @create 2024/7/19
 */
@Slf4j
@Component
public class SendAwardCustomer {

    @Value("${spring.rabbitmq.topic.send_award}")
    private String topic;

    @Resource
    private IAwardService awardService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_award}"))
    public void listener(String message) {
        try {
            log.info("监听用户奖品发送消息，发奖开始 topic: {} message: {}", topic, message);
            BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> eventMessage = JSON.parseObject(
                    message,
                    new TypeReference<BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage>>(){}.getType()
            );
            SendAwardMessageEvent.SendAwardMessage sendAwardMessage = eventMessage.getData();

            // 发放奖品
            DistributeAwardEntity distributeAwardEntity = DistributeAwardEntity.builder()
                    .userId(sendAwardMessage.getUserId())
                    .orderId(sendAwardMessage.getOrderId())
                    .awardId(sendAwardMessage.getAwardId())
                    .awardConfig(sendAwardMessage.getAwardConfig())
                    .build();
            awardService.distributeAward(distributeAwardEntity);

            log.info("监听用户奖品发送消息，发奖完成 topic: {} message: {}", topic, message);
        } catch (Exception e) {
            log.error("监听用户奖品发送消息，消费失败 topic: {} message: {}", topic, message);
            //throw e;
        }
    }

}
