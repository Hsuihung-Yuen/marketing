package cn.hhy.domain.credit.model.entity;

import cn.hhy.domain.credit.model.valobj.TaskStateVO;
import cn.hhy.domain.credit.event.CreditAdjustSuccessMessageEvent;
import cn.hhy.types.event.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hhy
 * @description 任务实体对象
 * @create 2024/7/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    /** 用户ID */
    private String userId;

    /** 消息主题 */
    private String topic;

    /** 消息编号 */
    private String messageId;

    /** 消息主体 */
    private BaseEvent.EventMessage<CreditAdjustSuccessMessageEvent.CreditAdjustSuccessMessage> message;

    /** 任务状态；create-创建、completed-完成、fail-失败 */
    private TaskStateVO state;

}
