package cn.hhy.trigger.listener;

import cn.hhy.domain.activity.model.entity.SkuRechargeEntity;
import cn.hhy.domain.activity.service.IRaffleActivityAccountQuotaService;
import cn.hhy.domain.credit.model.entity.TradeEntity;
import cn.hhy.domain.credit.model.valobj.TradeNameVO;
import cn.hhy.domain.credit.model.valobj.TradeTypeVO;
import cn.hhy.domain.credit.service.ICreditAdjustService;
import cn.hhy.domain.rebate.event.SendRebateMessageEvent;
import cn.hhy.types.enums.ResponseCode;
import cn.hhy.types.event.BaseEvent;
import cn.hhy.types.exception.AppException;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Hhy
 * @description 监听；行为返利消息
 * @create 2024/7/23
 */
@Slf4j
@Component
public class RebateMessageCustomer {

    @Value("${spring.rabbitmq.topic.send_rebate}")
    private String topic;
    
    @Resource
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    
    @Resource
    private ICreditAdjustService creditAdjustService;

    @RabbitListener(queuesToDeclare = @Queue(value = "${spring.rabbitmq.topic.send_rebate}"))
    public void listener(String message) {

        try {
            log.info("监听用户行为返利消息 topic: {} message: {}", topic, message);

            // 1. 转换消息
            BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage> eventMessage = JSON.parseObject(
                    message,
                    new TypeReference<BaseEvent.EventMessage<SendRebateMessageEvent.RebateMessage>>(){}.getType()
            );
            SendRebateMessageEvent.RebateMessage rebateMessage = eventMessage.getData();

            // 2. 入账奖励
            switch (rebateMessage.getRebateType()) {
                case "sku":
                    SkuRechargeEntity skuRechargeEntity = SkuRechargeEntity.builder()
                            .userId(rebateMessage.getUserId())
                            .sku(Long.valueOf(rebateMessage.getRebateConfig()))
                            .outBusinessNo(rebateMessage.getBizId())
                            .build();
                    raffleActivityAccountQuotaService.createOrder(skuRechargeEntity);
                    break;
                case "integral":
                    TradeEntity tradeEntity = TradeEntity.builder()
                            .userId(rebateMessage.getUserId())
                            .tradeName(TradeNameVO.REBATE)
                            .tradeType(TradeTypeVO.FORWARD)
                            .amount(new BigDecimal(rebateMessage.getRebateConfig()))
                            .outBusinessNo(rebateMessage.getBizId())
                            .build();
                    creditAdjustService.createOrder(tradeEntity);
                    break;
            }
        } catch (AppException e) {
            if (ResponseCode.INDEX_DUP.getCode().equals(e.getCode())) {
                log.warn("监听用户行为返利消息，消费重复 topic: {} message: {}", topic, message, e);
                return;
            }
            throw e;
        } catch (Exception e) {
            log.error("监听用户行为返利消息，消费失败 topic: {} message: {}", topic, message, e);
            throw e;
        }
    }

}
