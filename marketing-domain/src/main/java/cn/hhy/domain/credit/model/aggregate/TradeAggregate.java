package cn.hhy.domain.credit.model.aggregate;

import cn.hhy.domain.credit.event.CreditAdjustSuccessMessageEvent;
import cn.hhy.domain.credit.model.entity.CreditAccountEntity;
import cn.hhy.domain.credit.model.entity.CreditOrderEntity;
import cn.hhy.domain.credit.model.entity.TaskEntity;
import cn.hhy.domain.credit.model.valobj.TaskStateVO;
import cn.hhy.domain.credit.model.valobj.TradeNameVO;
import cn.hhy.domain.credit.model.valobj.TradeTypeVO;
import cn.hhy.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @author Hhy
 * @description 交易聚合对象
 * @create 2024/7/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeAggregate {

    /** 用户ID */
    private String userId;

    /** 积分账户实体 */
    private CreditAccountEntity creditAccountEntity;

    /** 积分订单实体 */
    private CreditOrderEntity creditOrderEntity;

    /** 任务实体 - 补偿 MQ 消息 */
    private TaskEntity taskEntity;

    public static CreditAccountEntity createCreditAccountEntity(String userId, BigDecimal adjustAmount) {
        return CreditAccountEntity.builder()
                .userId(userId)
                .adjustAmount(adjustAmount)
                .build();
    }

    public static CreditOrderEntity createCreditOrderEntity(String userId,
                                                            TradeNameVO tradeName,
                                                            TradeTypeVO tradeType,
                                                            BigDecimal tradeAmount,
                                                            String outBusinessNo) {
        return CreditOrderEntity.builder()
                .userId(userId)
                .orderId(RandomStringUtils.randomNumeric(12))
                .tradeName(tradeName)
                .tradeType(tradeType)
                .tradeAmount(tradeAmount)
                .outBusinessNo(outBusinessNo)
                .build();
    }

    public static TaskEntity createTaskEntity(String userId,
                                              String topic,
                                              String messageId,
                                              BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message) {
        return TaskEntity.builder()
                .userId(userId)
                .topic(topic)
                .messageId(messageId)
                .message(message)
                .state(TaskStateVO.create)
                .build();
    }


}
